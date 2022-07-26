package com.example.codedjourney.fragment
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codedjourney.LoginActivity
import com.example.codedjourney.databinding.FragmentUserBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    lateinit var auth: FirebaseAuth
    private lateinit var imgUri: Uri

    private val binding get() = _binding!!


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        //kondisi user sedang login atau tidak
        if (user != null) {
            binding.edtEmail.setText(user.email)

            if (user.isEmailVerified){
                binding.iconVerify.visibility = View.VISIBLE
                binding.iconNotverify.visibility = View.GONE
            } else {
                binding.iconVerify.visibility = View.GONE
                binding.iconNotverify.visibility = View.VISIBLE
            }
        }
        binding.cviUser.setOnClickListener {
            goToCamera()
        }
        binding.btnLogout.setOnClickListener {
            btnLogout()
        }
        binding.btnVerify.setOnClickListener {
            emailVerification()
        }
    }

    private fun emailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent?.resolveActivity(it).also {
                    startActivityForResult(intent, REQ_CAM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && requestCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImgToFirebase(imgBitmap)
        }

    }

    private fun uploadImgToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val img = baos.toByteArray()
        ref.putBytes(img)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener { Task->
                        Task.result.let{ Uri ->
                            imgUri = Uri
                            binding.cviUser.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun btnLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
    companion object {
        const val REQ_CAM = 100
    }
}

