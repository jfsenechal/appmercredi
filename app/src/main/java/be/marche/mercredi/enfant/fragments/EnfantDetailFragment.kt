package be.marche.mercredi.enfant.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.MercrediViewModel
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.utils.FileHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.enfant_detail_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class EnfantDetailFragment : Fragment() {

    private val PERMISSION_READ_EXTERNAL_STORAGE = 0
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
        inflater.inflate(R.menu.enfant, menu)
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

        enfantPhotoView.setOnClickListener {
            setupPermissions()
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
                findNavController().navigate(R.id.action_enfantDetailFragment_to_enfantEditFragment)
                return true
            }
            R.id.btnSante -> {
                findNavController().navigate(R.id.action_enfantDetailFragment_to_santeFragment)
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

                    val selectedImage = data?.data
                    if (selectedImage != null) {
                        val fileHelper = FileHelper()
                        val realPath = fileHelper.getPathFromURI(this.context!!, selectedImage)

                        Timber.i("zeze path %s", realPath)
                        if (realPath != null) {

                            val file = fileHelper.createFile(realPath)
                            val requestBody = fileHelper.createRequestBody(file)
                            val part = fileHelper.createPart(file, requestBody)

                            mercrediViewModel.uploadImage(enfant.id, part, requestBody)

                            enfantPhotoView.setImageURI(selectedImage)

                            enfant.photoUrl =
                                "https://www.marche.be/administration/files/2012/07/logo_au_format_jpg_grand_medium.jpg";
                            //viewModelEnfant.save(enfant)
                        }
                    }
                }
            }
        }
    }

    private fun setupPermissions() {
        Timber.i("zeze demarrage auto")
        val permission = ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {

            Timber.i("zeze Permission to read denied")

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_READ_EXTERNAL_STORAGE
            )

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // Explain to the user why we need to read the contacts

            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //callPhone()
                }
            }
        }
    }

    private fun updateUi(enfant: Enfant) {
        Picasso.get()
            .load(enfant.photoUrl)
            .placeholder(R.drawable.ic_image_black_24dp)
            .into(enfantPhotoView)

        enfantNom.text = enfant.nom
        enfantPrenom.text = enfant.prenom
        enfantBirthday.text = enfant.birthday
        enfantNumeroNational.text = enfant.numeroNational
        enfantRemarques.text = enfant.remarques
    }

}
