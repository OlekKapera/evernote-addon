package com.aleksanderkapera.evernoteaddon.ui

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.util.INTENT_ACTION_OPEN_MAIN_FRAGMENT
import com.aleksanderkapera.evernoteaddon.util.INTENT_OPEN_MAIN_ACTIVITY

/**
 * Implementation of App Widget functionality.
 */
class AddNoteAppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
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
        getMainFragmentIntent(context)
    )

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getMainFragmentIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainActivity::class.java).putExtra(
        "extra",
        INTENT_ACTION_OPEN_MAIN_FRAGMENT
    )
    return PendingIntent.getActivity(context, INTENT_OPEN_MAIN_ACTIVITY, intent, 0)
}