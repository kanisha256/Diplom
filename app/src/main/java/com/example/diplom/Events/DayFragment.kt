package com.example.diplom.Events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R


class DayFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var dayAdapter: DayAdapter? = null
    private var dayList: MutableList<Day>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        dayList = mutableListOf(
            Day("Пн", arrayOf("Наведи для выполнения", "Нажми и редактируй", "Перетащи на следующий день")),
            Day("Вт", arrayOf("Выбери цвет", "Надеюсь тебе понравится")),
            Day("Ср", arrayOf("Это все!")),
            Day("Чт", arrayOf()),
            Day("Пт", arrayOf("Не забудь сохранить")),
            Day("Сб", arrayOf()),
            Day("Вс", arrayOf())
        )

        dayAdapter = DayAdapter(dayList!!)
        recyclerView?.adapter = dayAdapter

        return view
    }
}

