package edu.cs.niu.anuraag.mp3player;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;



class MP3Filter implements FilenameFilter
{

	@Override
	public boolean accept(File dir, String name) 
	{
		// finds the file with mp3 extension
		return (name.endsWith(".mp3"));
	}
	
}


public class MainActivity extends ListActivity
{
	Button btn_stop;
	private static final String SD_PATH = new String("/sdcard/");
	private MediaPlayer mp = new MediaPlayer();
	private List<String> songs = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UpdatePlayList();
		
		btn_stop = (Button)findViewById(R.id.btn_stop);
		
		btn_stop.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				mp.stop();
				
			}
		});
	}

	private void UpdatePlayList() 
	{
		
		// uses mp3 filter class to find mp3 files on sdcard
		File home = new File(SD_PATH);
		if(home.listFiles(new MP3Filter()).length > 0)
		{	
			//put all music file names in the array list
			for(File file:home.listFiles(new MP3Filter()))
			{
				songs.add(file.getName());
			}
		}
		//connect the names of the songs in the arraylist to the list view
		
		ArrayAdapter<String> songList = 
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songs);
		
		setListAdapter(songList);
	}
	
	@Override
	protected void onListItemClick(ListView list, View v, int pos, long id)	
	{
		try
		{
			mp.reset();
			mp.setDataSource(SD_PATH + songs.get(pos));
			mp.prepare();
			mp.start();
		}
		catch(IOException e)
		{
			Log.d("Error", e.getMessage());
		}//end catch
	}//end onListItemClick
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

