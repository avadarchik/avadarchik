package com.example.saifapp5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.saifapp5.databinding.ActivityListenTestBinding

class ListenTestActivity : AppCompatActivity() {
    lateinit var name: TextView
    private lateinit var binding: ActivityListenTestBinding
    private lateinit var list: ArrayList<QuestionModel>
    private var count: Int = 0
    private var score = 0
    var userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListenTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = findViewById(R.id.NameListenTest)
        list = ArrayList<QuestionModel>()
        list.add(QuestionModel("A", "[a] А", "[aɪ] Ай", "[eɪ] Эй", "[eɪ] Эй"))
        list.add(QuestionModel("B", "[be] Бэ", "[biː] Би", "[b] Б", "[biː] Би"))
        list.add(QuestionModel("C", "[siː] Си", "[c] С", "[ce] Сэ", "[siː] Си"))
        list.add(QuestionModel("D", "[d] Д", "[de] Дэ", "[di:] Ди", "[di:] Ди"))
        list.add(QuestionModel("E", "[е] Е", "[iː] И ", "[ie] Ие", "[iː] И"))
        list.add(QuestionModel("F", "[f] Ф", "[ef] Эф", "[fe] Фэ", "[ef] Эф"))
        list.add(QuestionModel("G", "[jiː] Жи", "[d3i:] Джи", "[g] Г", "[d3iː] Джи"))
        list.add(QuestionModel("H", "[eich] Ейч", "[h:] H", "[eɪtʃ] Эйч", "[eɪtʃ] Эйч"))
        list.add(QuestionModel("I", "[i] И", "[ai] Ай", "[ii] Ий", "[ai] Ай"))
        list.add(QuestionModel("J", "[dje] Дже", "[j] Ж", "[d3ei] Джей", "[d3ei] Джей"))
        list.add(QuestionModel("K", "[kei] Кэй", "[ki] Ки", "[k] К", "[kei] Кэй"))
        list.add(QuestionModel("L", "[eli] Эль", "[l] Л", "[el] Эл", "[el] Эл"))
        list.add(QuestionModel("M", "[ме] Ме", "[em] Эм", "[м] М", "[em] Эм"))
        list.add(QuestionModel("N", "[en] Эн", "[n] Н", "[an] Ан", "[en] Эн"))
        list.add(QuestionModel("O", "[о] О", "[əʊ] Оу", "[оо] О", "[əʊ] Оу"))
        list.add(QuestionModel("P", "[piː] Пи", "[pe:] Пэ", "[p] П", "[piː] Пи"))
        list.add(QuestionModel("Q", "[kjuː] Кью", "[ku:] Ку", "[q] Кю", "[kjuː] Кью"))
        list.add(QuestionModel("R", "[r] Р", "[a:] Ar", "[ri] Ри", "[a:] ar"))
        list.add(QuestionModel("S", "[s] С", "[si] Си", "[es] Эс", "[es] Эс"))
        list.add(QuestionModel("T", "[te] Тэ", "[ti:] Ти", "[t] Т", "[ti:] Ти"))
        list.add(QuestionModel("U", "[ju:] ю", "[u] у", "[iu] иу", "[ju:] ю"))
        list.add(QuestionModel("V", "[v] В", "[vi:] Ви", "[м] М", "[vi:] Ви"))
        list.add(QuestionModel("W", "[vei] Вэй", "[w] В", "[dʌbljuː] Дабл-ю", "[dʌbljuː] Дабл-ю"))
        list.add(QuestionModel("X", "[ics] Икс", "[ixc] Ихс", "[eks] Экс", "[eks] Экс"))
        list.add(QuestionModel("Y", "[Yei] Уей", "[waɪ] Уай", "[Y] У", "[waɪ] Уай"))
        list.add(QuestionModel("Z", "[zɛd] Зэд", "[ziː] Зи", "[zz:] Зз", "[zɛd] Зэд"))

        binding.Question.setText(list.get(0).Question)
        binding.btnAnsverA.setText(list.get(0).btnAnsverA)
        binding.btnAnsverB.setText(list.get(0).btnAnsverB)
        binding.btnAnsverC.setText(list.get(0).btnAnsverC)

        binding.btnAnsverA.setOnClickListener {
            nextData(binding.btnAnsverA.text.toString())
        }
        binding.btnAnsverB.setOnClickListener {
            nextData(binding.btnAnsverB.text.toString())
        }
        binding.btnAnsverC.setOnClickListener {
            nextData(binding.btnAnsverC.text.toString())
        }
        userService.Init(this)
        name.setText(userService.userLastName)
    }

    private fun nextData(i: String) {
        if (count < list.size) {

            if (list.get(count).RigthAnsver.equals(i)) {
                score++
            }
        }

        count++
        if (count >= list.size) {
            val intent = Intent(this@ListenTestActivity, FinishTestActivity::class.java)
            intent.putExtra("Результат", score)
            startActivity(intent)
            finish()
        } else {
            binding.Question.setText(list.get(count).Question)
            binding.btnAnsverA.setText(list.get(count).btnAnsverA)
            binding.btnAnsverB.setText(list.get(count).btnAnsverB)
            binding.btnAnsverC.setText(list.get(count).btnAnsverC)
        }
    }

    fun BacktoLayoutSignIn(view: View) {
        val intent = Intent(this@ListenTestActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}
