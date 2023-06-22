package com.example.telinhas.home.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentReminderBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class ReminderFragment : Fragment() {

    private lateinit var binding: FragmentReminderBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentReminderBinding.inflate(layoutInflater)

        binding.buttonSetReminder.setOnClickListener {
            showTimePickerDialog()
        }

        binding.buttonNext.setOnClickListener { view ->
            setAlarm(view)
            findNavController().popBackStack()
        }

        createNotification()

        return binding.root
    }

    private fun setAlarm(view: View) {

        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        message(view, GenerationConstants.Reminder.REMINDER_ACTIVATED, Color.GREEN)
    }

    private fun createNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = GenerationConstants.Reminder.NAME
            val description = GenerationConstants.Reminder.DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(GenerationConstants.Reminder.ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                requireContext(), NotificationManager::class.java
            )
            notificationManager!!.createNotificationChannel(channel)
        }

    }

    private fun showTimePickerDialog() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText(GenerationConstants.Reminder.SELECT_THE_TIME)
            .build()

        picker.show(childFragmentManager, GenerationConstants.Reminder.ID)

        picker.addOnPositiveButtonClickListener {

            if (picker.hour > 12) {
                binding.textViewHour.text =
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + " PM"

            } else {
                binding.textViewHour.text =
                String.format("%02d", picker.hour) + " : " + String.format(
                    "%02d",
                    picker.minute
                ) + " AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }
    }

    private fun message(view: View, msg: String, color: Int) {
        val snack = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(color)
        snack.setTextColor(Color.WHITE)
        snack.show()
    }


}