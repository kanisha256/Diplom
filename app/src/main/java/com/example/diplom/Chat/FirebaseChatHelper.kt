package com.example.diplom.Chat

import com.google.firebase.database.*

class FirebaseChatHelper(private val database: FirebaseDatabase, private val roomName: String) {

    private val messagesReference: DatabaseReference = database.reference.child(roomName)

    fun sendMessage(message: String, userName: String) {
        val messageId = messagesReference.push().key
        val chatMessage = ChatMessage(message, userName)
        messagesReference.child(messageId!!).setValue(chatMessage)
    }


    fun setMessagesListener(listener: (List<ChatMessage>) -> Unit) {
        messagesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<ChatMessage>()
                for (childSnapshot in snapshot.children) {
                    val message = childSnapshot.getValue(ChatMessage::class.java)
                    message?.let { messages.add(it) }
                }
                listener(messages)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

data class ChatMessage(val message: String = "", val userName: String = "")
