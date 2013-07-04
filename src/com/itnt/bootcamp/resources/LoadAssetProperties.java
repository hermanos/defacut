package com.itnt.bootcamp.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.itnt.bootcamp.R;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;


/**
 * <p>Helper class to load an asset property file</p>
 * @author GabiRadu
 */
public class LoadAssetProperties {

	public Properties loadRESTApiFile(Resources resources, String filename, Context context) {
		// Read from the /assets directory
		AssetManager assetManager = resources.getAssets();
		try {
			InputStream inputStream = assetManager.open(filename);
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			System.err.println(context.getString(R.string.failedtoopenpropertyfile));
			e.printStackTrace();
		}
		return null;
	}
}