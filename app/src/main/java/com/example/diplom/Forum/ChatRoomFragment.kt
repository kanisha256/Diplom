package com.example.diplom.Forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.Chat.ChatFragment
import com.example.diplom.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatRoomFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatRoomAdapter
    private val database = FirebaseDatabase.getInstance()
    private val roomsRef = database.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_room, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ChatRoomAdapter { clickedRoom ->
            openChatRoom(clickedRoom)
        }

        recyclerView.adapter = adapter

        val roomsRef = database.reference.child("chatRooms")

        roomsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rooms = mutableListOf<ChatRoom>()
                for (childSnapshot in snapshot.children) {
                    val roomName = childSnapshot.key
                    val room = ChatRoom(roomName.orEmpty(), roomName.orEmpty())
                    rooms.add(room)
                }
                adapter.submitList(rooms)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })



        val createRoomButton = view.findViewById<Button>(R.id.createRoomButton)
        createRoomButton.setOnClickListener {
            showCreateRoomDialog()
        }

        return view
    }

    private fun showCreateRoomDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Создать комнату")

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("Создать") { _, _ ->
            val roomName = input.text.toString()
            if (roomName.isNotEmpty()) {
                createChatRoom(roomName)
            } else {
                Toast.makeText(requireContext(), "Введите название комнаты", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun createChatRoom(roomName: String) {
        val roomsRef = database.reference.child("chatRooms").child(roomName)
        val newRoom = ChatRoom(roomName, roomName)
        roomsRef.setValue(newRoom)
    }

    private fun openChatRoom(room: ChatRoom) {
        val chatFragment = ChatFragment()
        val bundle = Bundle().apply {
            putString("roomName", room.roomName)
        }
        chatFragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, chatFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
