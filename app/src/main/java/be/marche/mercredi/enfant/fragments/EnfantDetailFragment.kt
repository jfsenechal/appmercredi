package be.marche.mercredi.enfant.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.MercrediViewModel
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Enfant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.enfant_detail_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import okhttp3.RequestBody
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.Response
import timber.log.Timber
import java.io.File


class EnfantDetailFragment : Fragment() {

    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    val mercrediViewModel: MercrediViewModel by inject()
    lateinit var enfant: Enfant

    companion object {
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        fun newInstance() = EnfantDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.enfant_detail_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelEnfant.enfant?.observe(this, Observer { enfant ->
            this.enfant = enfant
            mercrediViewModel.getEcoleById(enfant.ecoleId).observe(this, Observer { ecole ->
                enfantEcole.text = ecole.nom
            })
            mercrediViewModel.getAnneeScolaireByName(enfant.anneeScolaire).observe(this, Observer {
                enfantAnneeScolaire.text = it.nom
            })
            updateUi(enfant)
        })

        enfantPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }

        btnAddPresence.setOnClickListener {
            findNavController().navigate(R.id.action_enfantDetailFragment_to_presenceFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnEdit -> {
                startEdit()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                    val contentURI = data!!.data
                    val thumbnail: Bitmap = data.getParcelableExtra("data")
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    //todo post to server
                    postServer(contentURI)
                    enfant.photoUrl =
                            "https://www.marche.be/administration/files/2012/07/logo_au_format_jpg_grand_medium.jpg";
                    viewModelEnfant.save(enfant)
                }
            }
        }
    }

    private fun postServer(contentURI: Uri) {
        Timber.i("zeze $contentURI")
        var MEDIA_TYPE_PNG: MediaType = MediaType.parse("image/png")!!;
        var file = File(contentURI.toString())
        var requestBody: RequestBody = RequestBody.create(MEDIA_TYPE_PNG, file)

        mercrediViewModel.uploadImage(enfant, requestBody)
    }

    private fun startEdit() {
        findNavController().navigate(R.id.action_enfantDetailFragment_to_enfantEditFragment)
    }

    private fun updateUi(enfant: Enfant) {
        Picasso.get()
            .load(enfant.photoUrl)
            .placeholder(R.drawable.ic_image_black_24dp)
            .into(enfantPhoto)

        enfantNom.text = enfant.nom
        enfantPrenom.text = enfant.prenom
        enfantBirthday.text = enfant.birthday
        enfantNumeroNational.text = enfant.numeroNational
        enfantRemarques.text = enfant.remarques
    }

}
