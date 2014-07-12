package com.infm.readit.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import com.infm.readit.database.LastReadContentProvider;
import com.infm.readit.database.LastReadDBHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by infm on 7/1/14. Enjoy ;)
 */
public class StorageCheckerService extends IntentService {

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public StorageCheckerService(String name){
		super(name);
	}

	public StorageCheckerService(){
		super("StorageCheckerService");
	}

	@Override
	protected void onHandleIntent(Intent intent){
		ContentResolver contentResolver = getContentResolver();
		Map<String, Integer> mapDB = getBaseData(contentResolver);
		processFolder(mapDB, contentResolver);
	}

	private Map<String, Integer> getBaseData(ContentResolver contentResolver){
		Map<String, Integer> result = new HashMap<String, Integer>();
		Cursor cursor = contentResolver.query(LastReadContentProvider.CONTENT_URI,
				new String[]{LastReadDBHelper.KEY_ROWID, LastReadDBHelper.KEY_PATH},
				null, null, null);
		while (cursor.moveToNext())
			result.put(cursor.getString(1), cursor.getInt(0));
		cursor.close();
		return result;
	}

	private void processFolder(Map<String, Integer> baseData, ContentResolver contentResolver){
		File homeDir = getFilesDir();
		File[] files = homeDir.listFiles();
		for (File file : files)
			if (file.exists() && !baseData.containsKey(file.getAbsolutePath()))
				file.delete();
		for (Map.Entry<String, Integer> entry : baseData.entrySet()){
			String path = entry.getKey();
            if (!(new File(path)).exists())
                contentResolver.delete(ContentUris.withAppendedId(LastReadContentProvider.CONTENT_URI, entry.getValue()),
						null, null);
		}
	}
}
