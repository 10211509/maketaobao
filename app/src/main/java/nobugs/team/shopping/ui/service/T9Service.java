package nobugs.team.shopping.ui.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Service;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;

import com.hisun.phone.core.voice.util.Log4Util;
import com.voice.demo.CCPApplication;
import com.voice.demo.contacts.model.ContactBean;
import com.voice.demo.ui.CCPHelper;

public class T9Service extends Service {

	private static AsyncQueryHandler asyncQuery;
	@Override
	public void onCreate() {
		super.onCreate();
		long currentTimeMillis = System.currentTimeMillis();
		
		// 定时 30秒查询
		System.out.println("T9Service-begin");

		// 注册一个内容观察者 观察数据库内容的改变
		getContentResolver().registerContentObserver(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, true,
				new ContactsContentObserver(new Handler()));
		if (asyncQuery == null)
			asyncQuery = new MyAsyncQueryHandler(getContentResolver());
		initSQL();
		
//		Log4Util.d(CCPHelper.DEMO_TAG, "Time : " + (System.currentTimeMillis() - currentTimeMillis));
	}

	public void onStart(Intent intent, int startId) {
	}

	public void onDestroy() {
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	public void onRebind(Intent intent) {
	}

	public boolean onUnbind(Intent intent) {
		return true;
	}

	public static void initSQL() {
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人的Uri
		String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
				ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY }; // 查询的列
		asyncQuery.startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc"); // 按照sort_key升序查询

		Uri uri2 = ContactsContract.Contacts.CONTENT_URI; // 联系人的Uri
		String[] projection2 = { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts.PHOTO_ID,ContactsContract.Contacts.HAS_PHONE_NUMBER}; // 查询的列
		asyncQuery.startQuery(1, null, uri2, projection2, null, null, null); // 按照sort_key升序查询
	}

	private List<ContactBean> contactslist;

	private class MyAsyncQueryHandler extends AsyncQueryHandler {

		private Set<Integer> contactIdsSet;

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}

		
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			
			
			if (token == 1) {
				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToPosition(i);
						int hasPhome = cursor.getInt(3);
						if(hasPhome==0){//添加没有号码的那些
							int contactId = cursor.getInt(0);
							String name = cursor.getString(1);
							Long photoId = cursor.getLong(2);
							ContactBean cb = new ContactBean();
							cb.setDisplayName(name);
							cb.setContactId(contactId);
							cb.setPhotoId(photoId);
							contactslist.add(cb);
							contactIdsSet.add(contactId);
						}
					}
				}
				 
			}else if(token == 0) {
				contactslist = new ArrayList<ContactBean>();
				contactIdsSet = new HashSet<Integer>();
				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToPosition(i);
						String name = cursor.getString(1);
						String number = cursor.getString(2);
						String sortKey = cursor.getString(3);
						int contactId = cursor.getInt(4);
						Long photoId = cursor.getLong(5);
						String lookUpKey = cursor.getString(6);
						ContactBean cb = new ContactBean();
						cb.setDisplayName(name);
						cb.setPhoneNum(trimAll(number));
						cb.setSortKey(sortKey);
						cb.setContactId(contactId);
						cb.setPhotoId(photoId);
						cb.setLookUpKey(lookUpKey);

						if (!contactIdsSet.contains(contactId)) {
							contactslist.add(cb);
							contactIdsSet.add(contactId);
						} else {
							for (ContactBean c : contactslist) {
								if (c.getContactId() == contactId) {
									ArrayList<String> morephoneNumber = new ArrayList<String>();
									String[] morenumbers = c.getMorenumbers();
									if (morenumbers != null) {
										for (String s : morenumbers) {
											morephoneNumber.add(trimAll(s));
										}
									}
									morephoneNumber.add(trimAll(number));
									c.setMorenumbers(morephoneNumber
											.toArray(new String[0]));
								}
							}
						}
					}

					
				}
			}
			
			Collections.sort(contactslist);
			CCPApplication instance = CCPApplication.getInstance();
			instance.setContactBeanList(contactslist);
			if(cursor!=null){
				cursor.close();
			}
			 
		}

	}



	/**
	 * 利用内容观察者 观察数据的变化 只有数据库发生变化的时候 才重新获取数据库里面的内容
	 * 
	 */
	private class ContactsContentObserver extends ContentObserver {

		private long time;

		public ContactsContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {

			contactslist = new ArrayList<ContactBean>();
			initSQL();
			// }
			time = new Date().getTime();
			super.onChange(selfChange);
		}

	}
	public  String trimAll(String s) {
		while (s.contains(" ")) {
			s = s.replace(" ", "");
		}
		return s;
	}
}
