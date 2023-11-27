package com.armaan.coffeemasters.sign_in

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.armaan.coffeemasters.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

@SuppressLint("StaticFieldLeak")
class AuthUIClient(
    private val context: Context,
    private val googleOneTapClient: SignInClient
){
    private val auth = Firebase.auth

/*  --------------------------------------- Google Sign-In Authentication Begins Here ---------------------------------------*/

//  Creates a GoogleSignIn Intent by building the SignIn Request
    suspend fun googleSignIn() : IntentSender? {
        val result = try {
            googleOneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
    return result?.pendingIntent?.intentSender
    }


//  Signs out the user
    suspend fun googleSignOut() {
        try {
            googleOneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

//   Uses the Google SignIn Intent to actually sign in and stores the user info into the UserData object
    suspend fun googleSignInWithIntent(intent: Intent) : SignInResult {
    val credential = googleOneTapClient.getSignInCredentialFromIntent(intent)
    val googleIdToken = credential.googleIdToken
    val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
    return try {
        val user = auth.signInWithCredential(googleCredentials).await().user
        SignInResult(
            data = user?.run {
                UserData(
                    userId = uid,
                    username = displayName,
                    pfpUrl = photoUrl?.toString()
                )
            },
            errorMessage = null
        )
    } catch (e: Exception) {
        e.printStackTrace()
        if (e is CancellationException) throw e
        SignInResult(
            data = null,
            errorMessage = e.message
        )
    }
    }



//    Builds Google Sign-In Request
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }


/*  --------------------------------------- Google Sign-In Authentication Ends Here ---------------------------------------*/




    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            pfpUrl = photoUrl.toString()
        )
    }



}