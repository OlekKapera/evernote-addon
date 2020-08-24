package com.aleksanderkapera.evernoteaddon.ui

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.util.INTENT_OPEN_MAIN_FRAGMENT
import com.aleksanderkapera.evernoteaddon.viewmodel.MainFragmentViewModel

/**
 * Implementation of App Widget functionality.
 */
class AddNoteAppWidget : AppWidgetProvider() {

    private lateinit var viewModel: MainFragmentViewModel

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        viewModel =
            ViewModelProvider(appWidgetManager as ViewModelStoreOwner).get(MainFragmentViewModel::class.java)

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.app_widget_add_note)

    // open main fragment upon widget click
    views.setOnClickPendingIntent(
        R.id.addWidget_button,
        PendingIntent.getActivity(
            context,
            INTENT_OPEN_MAIN_FRAGMENT,
            Intent(context, MainActivity::class.java),
            0
        )
    )

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}