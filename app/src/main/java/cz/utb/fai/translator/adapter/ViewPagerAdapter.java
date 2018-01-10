package cz.utb.fai.translator.adapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import cz.utb.fai.translator.R;  //R je trieda cez ktoru sme schopni adresovat akykolvek prvok z layoutu

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    public ViewPagerAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public Object instantiateItem(ViewGroup collection, int position){
        int resId;
// set PAGE HOME as default
        if(position == 0){
            resId = R.id.page_home;
        }
        else if(position == 1){
            resId = R.id.page_history;
        }
        else {
            resId = R.id.page_contact;
        }
        return collection.findViewById(resId);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {}
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public int getCount() {
        return 3;  //vraciame rovno tri stranky, dalo by sa to prepisat inak
    }
    @Override
    public CharSequence getPageTitle(int position) {   //meni sa horny titulok podla toho aky je vybrany
        if(position == 0){
            return mContext.getResources().getString(R.string.home);
        }
        else if(position == 1){
            return mContext.getResources().getString(R.string.history);
        }
        else if(position == 2){
            return mContext.getResources().getString(R.string.contact);
        }
        return super.getPageTitle(position);
    }
}