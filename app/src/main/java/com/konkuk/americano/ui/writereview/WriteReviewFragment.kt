package com.konkuk.americano.ui.writereview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.konkuk.americano.R
import com.konkuk.americano.databinding.FragmentWriteReviewBinding


class WriteReviewFragment : Fragment() {
    private lateinit var binding: FragmentWriteReviewBinding

    val flavourChecked: ArrayList<Boolean> = arrayListOf(true, false, false, false, false)
    val sourChecked: ArrayList<Boolean> = arrayListOf(true, false, false, false, false)
    val bitterChecked: ArrayList<Boolean> = arrayListOf(true, false, false, false, false)
    val aftertasteChecked: ArrayList<Boolean> = arrayListOf(true, false, false, false, false)
    val zestChecked: ArrayList<Boolean> = arrayListOf(true, false, false, false, false)
    val balanceChecked: ArrayList<Boolean> = arrayListOf(true, false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWriteReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        initRadioGroup()

        initAddImagesBtn()

        initWriteBtn()
    }

    private fun initAddImagesBtn() {
        // 갤러리 or 카메라로 이미지 선택 각각 버튼에 달아주기
    }

    private fun initWriteBtn() {
        binding.writeReviewWriteBtn.setOnClickListener {
            writeReview()
        }
    }

    // radiogroup 내의 radiobtn 가져오는 방법 찾아서 코드 짧게 바꿔보기
    private fun initRadioGroup() {
        binding.apply {
            writeReviewFlavourRG.setOnCheckedChangeListener { _, checkedId ->
                resetBtnImage(arrayListOf(writeReviewFlavourRB1, writeReviewFlavourRB2, writeReviewFlavourRB3, writeReviewFlavourRB4, writeReviewFlavourRB5))
                when(checkedId) {
                    writeReviewFlavourRB1.id -> changeBtnImage(writeReviewFlavourRB1, 1)
                    writeReviewFlavourRB2.id -> changeBtnImage(writeReviewFlavourRB2, 2)
                    writeReviewFlavourRB3.id -> changeBtnImage(writeReviewFlavourRB3, 3)
                    writeReviewFlavourRB4.id -> changeBtnImage(writeReviewFlavourRB4, 4)
                    writeReviewFlavourRB5.id -> changeBtnImage(writeReviewFlavourRB5, 5)
                    else -> return@setOnCheckedChangeListener
                }
            }
            writeReviewSourRG.setOnCheckedChangeListener { _, checkedId ->
                resetBtnImage(arrayListOf(writeReviewSourRB1, writeReviewSourRB2, writeReviewSourRB3, writeReviewSourRB4, writeReviewSourRB5))
                when(checkedId) {
                    writeReviewSourRB1.id -> changeBtnImage(writeReviewSourRB1, 1)
                    writeReviewSourRB2.id -> changeBtnImage(writeReviewSourRB2, 2)
                    writeReviewSourRB3.id -> changeBtnImage(writeReviewSourRB3, 3)
                    writeReviewSourRB4.id -> changeBtnImage(writeReviewSourRB4, 4)
                    writeReviewSourRB5.id -> changeBtnImage(writeReviewSourRB5, 5)
                    else -> return@setOnCheckedChangeListener
                }
            }
            writeReviewBitterRG.setOnCheckedChangeListener { _, checkedId ->
                resetBtnImage(arrayListOf(writeReviewBitterRB1, writeReviewBitterRB2, writeReviewBitterRB3, writeReviewBitterRB4, writeReviewBitterRB5))
                when(checkedId) {
                    writeReviewBitterRB1.id -> changeBtnImage(writeReviewBitterRB1, 1)
                    writeReviewBitterRB2.id -> changeBtnImage(writeReviewBitterRB2, 2)
                    writeReviewBitterRB3.id -> changeBtnImage(writeReviewBitterRB3, 3)
                    writeReviewBitterRB4.id -> changeBtnImage(writeReviewBitterRB4, 4)
                    writeReviewBitterRB5.id -> changeBtnImage(writeReviewBitterRB5, 5)
                    else -> return@setOnCheckedChangeListener
                }
            }
            writeReviewAftertasteRG.setOnCheckedChangeListener { _, checkedId ->
                resetBtnImage(arrayListOf(writeReviewAftertasteRB1, writeReviewAftertasteRB2, writeReviewAftertasteRB3, writeReviewAftertasteRB4, writeReviewAftertasteRB5))
                when(checkedId) {
                    writeReviewAftertasteRB1.id -> changeBtnImage(writeReviewAftertasteRB1, 1)
                    writeReviewAftertasteRB2.id -> changeBtnImage(writeReviewAftertasteRB2, 2)
                    writeReviewAftertasteRB3.id -> changeBtnImage(writeReviewAftertasteRB3, 3)
                    writeReviewAftertasteRB4.id -> changeBtnImage(writeReviewAftertasteRB4, 4)
                    writeReviewAftertasteRB5.id -> changeBtnImage(writeReviewAftertasteRB5, 5)
                    else -> return@setOnCheckedChangeListener
                }
            }
            writeReviewZestRG.setOnCheckedChangeListener { _, checkedId ->
                resetBtnImage(arrayListOf(writeReviewZestRB1, writeReviewZestRB2, writeReviewZestRB3, writeReviewZestRB4, writeReviewZestRB5))
                when(checkedId) {
                    writeReviewZestRB1.id -> changeBtnImage(writeReviewZestRB1, 1)
                    writeReviewZestRB2.id -> changeBtnImage(writeReviewZestRB2, 2)
                    writeReviewZestRB3.id -> changeBtnImage(writeReviewZestRB3, 3)
                    writeReviewZestRB4.id -> changeBtnImage(writeReviewZestRB4, 4)
                    writeReviewZestRB5.id -> changeBtnImage(writeReviewZestRB5, 5)
                    else -> return@setOnCheckedChangeListener
                }
            }
            writeReviewBalanceRG.setOnCheckedChangeListener { _, checkedId ->
                resetBtnImage(arrayListOf(writeReviewBalanceRB1, writeReviewBalanceRB2, writeReviewBalanceRB3, writeReviewBalanceRB4, writeReviewBalanceRB5))
                when(checkedId) {
                    writeReviewBalanceRB1.id -> changeBtnImage(writeReviewBalanceRB1, 1)
                    writeReviewBalanceRB2.id -> changeBtnImage(writeReviewBalanceRB2, 2)
                    writeReviewBalanceRB3.id -> changeBtnImage(writeReviewBalanceRB3, 3)
                    writeReviewBalanceRB4.id -> changeBtnImage(writeReviewBalanceRB4, 4)
                    writeReviewBalanceRB5.id -> changeBtnImage(writeReviewBalanceRB5, 5)
                    else -> return@setOnCheckedChangeListener
                }
            }
        }
    }

    private fun resetBtnImage(btnList: ArrayList<RadioButton>) {
        if (btnList.size != 5) return
        btnList[0].setBackgroundResource(R.drawable.value1)
        btnList[1].setBackgroundResource(R.drawable.value2)
        btnList[2].setBackgroundResource(R.drawable.value3)
        btnList[3].setBackgroundResource(R.drawable.value4)
        btnList[4].setBackgroundResource(R.drawable.value5)
    }

    private fun changeBtnImage(btn: RadioButton, value: Int) {
        val imgId = when(value) {
            1 -> R.drawable.value1_checked
            2 -> R.drawable.value2_checked
            3 -> R.drawable.value3_checked
            4 -> R.drawable.value4_checked
            5 -> R.drawable.value5_checked
            else -> R.drawable.value1_checked
        }

        btn.setBackgroundResource(imgId)
    }

    // 작성하기 버튼 listener 성공 여부 반환
    private fun writeReview(): Boolean {
        // 각 value 값 가져오기 (6개)
        // 상세 후기 가져오기
        // 이미지 있으면 가져오기 (최대 3장)
        // 서버로 send
        return false
    }
}