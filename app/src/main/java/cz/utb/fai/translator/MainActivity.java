package cz.utb.fai.translator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.utb.fai.translator.adapter.HistoryAdapter;
import cz.utb.fai.translator.adapter.ViewPagerAdapter;
public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    TextToSpeech toSpeech;   //premenna na speech
    int result;
    TextView viewText;
    String text;


    private List<String> mHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {  //metoda ktora sa prevola ak sa aktivita zacne vytvarat
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speech();
        init();
    }



    private void init(){
        mHistory = new ArrayList<String>();

        mViewPager = (ViewPager)findViewById(R.id.pager);   //vyberieme si podla tejto metody
        mViewPager.setAdapter(new ViewPagerAdapter(this));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout = (TabLayout)findViewById(R.id.tableLayout);
// adds view pager into tab layout
        mTabLayout.setupWithViewPager(mViewPager);

        new PageHome(this);

        // set recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view); //zinstancionave, mozeme s tym pracovat
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HistoryAdapter(mHistory); //vytvoreny adapter

        mRecyclerView.setAdapter(mAdapter); // nastavime ho nasmu recyclerview
    }

    public List<String> getHistoryList(){
        return this.mHistory;

    }

    public RecyclerView.Adapter returnAdapter(){
        return mAdapter;

    }


    //text to speech
    private void speech(){
        viewText=(TextView) findViewById(R.id.tvResult);
        toSpeech=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    result=toSpeech.setLanguage(Locale.UK);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not supported in your device",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void TTS(View view){
        switch (view.getId())
        {
            case R.id.bspeak:
                if(result==TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getApplicationContext(), "Not supported in your device",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String helptext = viewText.getText().toString();
                    text=helptext.replace("Překlad:","");
                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                }
                break;

            case R.id.bstop:
                if(toSpeech!=null)
                {
                    toSpeech.stop();
                }
                break;

        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(toSpeech!=null)
        {
            toSpeech.stop();
            toSpeech.shutdown();

        }
    }
    //koniec text to speech

    public void send_click(View v) {
        EditText name = (EditText) findViewById(R.id.your_name);
        EditText email = (EditText) findViewById(R.id.your_email);
        EditText subject = (EditText) findViewById(R.id.your_subject);
        EditText message = (EditText) findViewById(R.id.your_message);

        if (name.getText().toString().equals(""))
            name.setError("Mandatory");
        else if (email.getText().toString().equals(""))
            email.setError("Mandatory");
        else if (subject.getText().toString().equals(""))
            subject.setError("Mandatory");
        else if (message.getText().toString().equals(""))
            message.setError("Mandatory");
        else {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"mitis1991@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
            i.putExtra(Intent.EXTRA_TEXT, "Dobry den prajem Tomáš Milo, \n" + message.getText().toString() + "\n s pozdravom, " + email.getText().toString());

            try {
                startActivity(Intent.createChooser(i, "send email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "no email app found", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "error" + ex.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }





}