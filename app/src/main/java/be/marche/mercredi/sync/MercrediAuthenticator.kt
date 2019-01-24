package be.marche.mercredi.sync

import be.marche.mercredi.user.UserViewModel
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * OkHttpClient.Builder()
 *  .authenticator(MercrediAuthenticator(accessTokenProvider))
 *
 * Authentificateur qui tente d'actualiser le jeton d'accès du client.
 * Si une actualisation échoue et qu'un nouveau jeton ne peut pas recevoir d'erreur,
 * est remis à l'appelant. Cet authentifiant bloque toutes les demandes lorsqu'un jeton
 * l'actualisation est en cours. Les demandes en vol qui échouent avec un 401 sont
 * automatiquement réessayé.
 *
 */
class MercrediAuthenticator(private val userViewModel: UserViewModel) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // We need to have a token in order to refresh it.
        val token = userViewModel.token() ?: return null

        synchronized(this) {
            val newToken = userViewModel.token()

            // Check if the request made was previously made as an authenticated request.
            if (response.request().header("X-AUTH-TOKEN") != null) {

                // If the token has changed since the request was made, use the new token.
                if (newToken != token) {
                    return response.request()
                        .newBuilder()
                        .removeHeader("X-AUTH-TOKEN")
                        .addHeader("X-AUTH-TOKEN", newToken)
                        .build()
                }

                val updatedToken = userViewModel.refreshToken() ?: return null

                // Retry the request with the new token.
                return response.request()
                    .newBuilder()
                    .removeHeader("X-AUTH-TOKEN")
                    .addHeader("X-AUTH-TOKEN", updatedToken)
                    .build()
            }
        }
        return null
    }
}
