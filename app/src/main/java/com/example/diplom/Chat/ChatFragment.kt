package com.example.diplom.Chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.google.firebase.auth.FirebaseAuth

class ChatFragment : Fragment() {

    private lateinit var chatHelper: FirebaseChatHelper
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        val roomName = arguments?.getString("roomName")
        chatHelper = FirebaseChatHelper(database, roomName.toString())

        val messageEditText: EditText = view.findViewById(R.id.messageEditText)
        val sendButton: Button = view.findViewById(R.id.sendButton)
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        chatAdapter = ChatAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = chatAdapter
        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            auth = FirebaseAuth.getInstance()
            val userName = auth.currentUser!!.email.toString()
            if (message.isNotEmpty()) {
                chatHelper.sendMessage(message, userName)
                messageEditText.text.clear()
            }
        }


        chatHelper.setMessagesListener { messages ->
            chatAdapter.setMessages(messages)
            if (messages.isNotEmpty()) {
                recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

}

