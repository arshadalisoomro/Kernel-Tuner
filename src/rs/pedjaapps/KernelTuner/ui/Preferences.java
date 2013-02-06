package rs.pedjaapps.KernelTuner.ui;





import android.app.*;
import android.app.ActivityManager.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.preference.Preference.*;
import com.actionbarsherlock.app.*;
import rs.pedjaapps.KernelTuner.*;
import rs.pedjaapps.KernelTuner.services.*;

import com.actionbarsherlock.app.ActionBar;



public class Preferences extends SherlockPreferenceActivity
{

	private ListPreference bootPrefList;
	private EditTextPreference widgetPref;
	private ListPreference tempPrefList;
	private ListPreference notifPrefList;
	private CheckBoxPreference notifBox;
	private PreferenceScreen notifScreen;
	private CheckBoxPreference htcOneOverride;
	private ListPreference tisList;
	private CheckBoxPreference resetApp;
	private ListPreference themePrefList;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{        
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String them = sharedPrefs.getString("theme", "light");
		
			if(them.equals("light")){
				setTheme(R.style.Theme_Sherlock_Light);
			}
			else if(them.equals("dark")){
				setTheme(R.style.Theme_Sherlock);
				
			}
			else if(them.equals("light_dark_action_bar")){
				setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
				
			}
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences); 
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		themePrefList = (ListPreference) findPreference("theme");
        themePrefList.setDefaultValue(themePrefList.getEntryValues()[0]);
        String theme = themePrefList.getValue();
        if (theme == null) {
            themePrefList.setValue((String)themePrefList.getEntryValues()[0]);
            theme = themePrefList.getValue();
        }
        themePrefList.setSummary(themePrefList.getEntries()[themePrefList.findIndexOfValue(theme)]);


        themePrefList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                themePrefList.setSummary(themePrefList.getEntries()[themePrefList.findIndexOfValue(newValue.toString())]);
                return true;
            }
        }); 
		
		bootPrefList = (ListPreference) findPreference("boot");
        bootPrefList.setDefaultValue(bootPrefList.getEntryValues()[0]);
        String boot = bootPrefList.getValue();
        if (boot == null) {
            bootPrefList.setValue((String)bootPrefList.getEntryValues()[0]);
            boot = bootPrefList.getValue();
        }
        bootPrefList.setSummary(bootPrefList.getEntries()[bootPrefList.findIndexOfValue(boot)]);


        bootPrefList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                bootPrefList.setSummary(bootPrefList.getEntries()[bootPrefList.findIndexOfValue(newValue.toString())]);
                return true;
            }
        }); 
        
        widgetPref = (EditTextPreference) findPreference("widget_time");
        widgetPref.setDefaultValue(widgetPref.getText());
        String widget = widgetPref.getText();
        if (widget == null) {
        	widgetPref.setText((String)widgetPref.getText());
            widget = widgetPref.getText();
        }
        widgetPref.setSummary(widget+"min");


        widgetPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	widgetPref.setSummary(newValue.toString()+"min");
                return true;
            }
        }); 
        
        tempPrefList = (ListPreference) findPreference("temp");
        tempPrefList.setDefaultValue(tempPrefList.getEntryValues()[0]);
        String temp = tempPrefList.getValue();
        if (temp == null) {
        	tempPrefList.setValue((String)tempPrefList.getEntryValues()[0]);
        	temp = tempPrefList.getValue();
        }
        tempPrefList.setSummary(tempPrefList.getEntries()[tempPrefList.findIndexOfValue(temp)]);


        tempPrefList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	tempPrefList.setSummary(tempPrefList.getEntries()[tempPrefList.findIndexOfValue(newValue.toString())]);
                return true;
            }
        }); 
        
       
        
        notifPrefList = (ListPreference) findPreference("notif");
        notifPrefList.setDefaultValue(notifPrefList.getEntryValues()[0]);
        String notif = notifPrefList.getValue();
        if (notif == null) {
        	notifPrefList.setValue((String)notifPrefList.getEntryValues()[0]);
        	notif = notifPrefList.getValue();
        }
        notifPrefList.setSummary(notifPrefList.getEntries()[notifPrefList.findIndexOfValue(notif)]);


        notifPrefList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	notifPrefList.setSummary(notifPrefList.getEntries()[notifPrefList.findIndexOfValue(newValue.toString())]);
            	if(isNotificationServiceRunning()){
            	stopService(new Intent(Preferences.this, NotificationService.class));
            	startService(new Intent(Preferences.this, NotificationService.class));
            	}
                return true;
            }
        }); 
		
        notifBox = (CheckBoxPreference) findPreference("notificationService");
        notifBox.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	if(notifBox.isChecked()){
            		stopService(new Intent(Preferences.this, NotificationService.class));
                	
            	}
            	else if(notifBox.isChecked()==false){
            	startService(new Intent(Preferences.this, NotificationService.class));
            	}
            	
                return true;
            }
        }); 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
        	 getActionBar().setSubtitle("Application Preferences");
        }
        notifScreen = (PreferenceScreen)findPreference("notificationScreen");
        notifScreen.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
	                    Preferences.this);

					builder.setMessage(getResources().getString(R.string.notificatio_preferences_warning));

					builder.setIcon(R.drawable.ic_menu_recent_history);

					builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
							
							}
						});
					
					
					AlertDialog alert = builder.create();

					alert.show();
				return false;
			}
        	
        });
        htcOneOverride = (CheckBoxPreference) findPreference("htc_one_workaround");
        htcOneOverride.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				if(htcOneOverride.isChecked()){
				AlertDialog.Builder builder = new AlertDialog.Builder(
	                    Preferences.this);

					builder.setMessage(getResources().getString(R.string.htc_override_preferences_warning));

					builder.setIcon(R.drawable.ic_menu_info_details);

					builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
							
							}
						});
					
					
					AlertDialog alert = builder.create();

					alert.show();
				}
				return false;
			}
        	
        });
        
        resetApp = (CheckBoxPreference) findPreference("reset");
        resetApp.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				if(resetApp.isChecked()){
				AlertDialog.Builder builder = new AlertDialog.Builder(
	                    Preferences.this);

					builder.setMessage("This will not work if init.d is selected for restoring settings after reboot.\n\n init.d scripts are executed before this application can start");

					builder.setIcon(R.drawable.ic_menu_info_details);

					builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
							
							}
						});
					
					
					AlertDialog alert = builder.create();

					alert.show();
				}
				return false;
			}
        	
        });
		
		tisList = (ListPreference) findPreference("tis_open_as");
	//	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Preferences.this);
       // final SharedPreferences.Editor editor = prefs.edit();
		 tisList.setDefaultValue(notifPrefList.getEntryValues()[0]);
        String tis = tisList.getValue();
        if (tis == null) {
        	tisList.setValue((String)tisList.getEntryValues()[0]);
        	tis = tisList.getValue();
        }
        tisList.setSummary(tisList.getEntries()[tisList.findIndexOfValue(tis)]);

        tisList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					tisList.setSummary(tisList.getEntries()[tisList.findIndexOfValue(newValue.toString())]);
			/*	if(newValue.toString().equals("remember")){
					editor.putBoolean("tis_remember",true);
					editor.putString("tis_show_as",newValue.toString());
					editor.commit();
				}
				else{
					editor.putBoolean("tis_remember", false);
					editor.commit();
				}*/

					return true;
				}
			}); 
       
	}
	
	private boolean isNotificationServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (NotificationService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, KernelTuner.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        
	            
	    }
	    return super.onOptionsItemSelected(item);
	}
	
}