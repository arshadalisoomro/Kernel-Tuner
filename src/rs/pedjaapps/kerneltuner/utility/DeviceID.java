package rs.pedjaapps.kerneltuner.utility;

import android.os.Build;

import java.util.UUID;

public class DeviceID
{
    //private final String imei;
    //private final String phoneNumber;
    //private final String softwareVer;
    //private final String simSerial;
    //private final String subscriberId;
    //private final String androidID;

    /*public DeviceID(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        imei = telephonyManager.getDeviceId();
        phoneNumber = telephonyManager.getLine1Number();
        softwareVer = telephonyManager.getDeviceSoftwareVersion();
        simSerial = telephonyManager.getSimSerialNumber();
        subscriberId = telephonyManager.getSubscriberId();

        androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }*/

    /*public String getImei()
    {
        return imei;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getSimSerial()
    {
        return simSerial;
    }

    public String getSoftwareVer()
    {
        return softwareVer;
    }

    public String getSubscriberId()
    {
        return subscriberId;
    }

    public String getAndroidID()
    {
        return androidID;
    }

    @Override
    public String toString()
    {
        //return "PhoneID{" + "imei=" + imei + ", phoneNumber=" + phoneNumber + ", softwareVer=" + softwareVer + ", simSerial=" + simSerial + ", subscriberId=" + subscriberId + '}';
    	if(androidID != null)
    	{
    		return androidID;
    	}
    	else if(imei != null)
    	{
    		return imei;
    	}
    	else
    	{
    		return psudoUniqueId();
    	}
    }*/

    /**
     * Return pseudo unique ID
     * @return ID
     */
    public static String getPsuedoUniqueID()
    {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their phone or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" +
                (Build.BOARD.length() % 10)
                + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10)
                + (Build.DEVICE.length() % 10)
                + (Build.MANUFACTURER.length() % 10)
                + (Build.MODEL.length() % 10)
                + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their phone, there will be a duplicate entry
        String serial = null;
        try
        {
            serial = Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        catch (Exception e)
        {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
