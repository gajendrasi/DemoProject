package gaurav.example.interview_demo_project;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TeacherDetailInformationAdapter extends ArrayAdapter<TeacherDetailInformationData> {

    Context mContext;
    ArrayList<TeacherDetailInformationData> teacherDetailInformationData;
    List<TeacherDetailInformationAdapter> TeacherSpecilityInfos;
    int resource;
    ImageView docImage;
    TextView teachername,teachermobile,teacherEx;
    List<TeacherDetailInformationData> templist;

    public TeacherDetailInformationAdapter(Context context,int resource, ArrayList<TeacherDetailInformationData> teacherDetailInformationData) {
        super(context, resource,teacherDetailInformationData);
        this.mContext=context;
        this.resource=resource;
        this.teacherDetailInformationData=teacherDetailInformationData;
        templist = new ArrayList<>(teacherDetailInformationData);
    }

    public int getCount() {
        if (teacherDetailInformationData != null)
            return teacherDetailInformationData.size();
        return 0;
    }

    public TeacherDetailInformationData getItem(int position) {
        if (teacherDetailInformationData != null)
            return teacherDetailInformationData.get(position);

        return null;
    }

    public long getItemId(int position) {
        if (teacherDetailInformationData != null)
            return teacherDetailInformationData.get(position).hashCode();
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.teacher_list_items,null,true);

        }
        TeacherDetailInformationData TeacherDetailInformationData=getItem(position);

        //docImage=(ImageView) convertView.findViewById(R.id.DocImage);
        teachername=(TextView)convertView.findViewById(R.id.DocName);
        teachermobile= (TextView) convertView.findViewById(R.id.DocSpecialization);
        teacherEx=(TextView) convertView.findViewById(R.id.DocExperince);


        String imageUrl=TeacherDetailInformationData.getDocImage();
        //Picasso.with(mContext).load("http://www.jantacare.com/upload/"+imageUrl).into(docImage);
        teachername.setText(TeacherDetailInformationData.getTeacherName());
        teachermobile.setText(TeacherDetailInformationData.getTeacherMobile());
        teacherEx.setText(TeacherDetailInformationData.getTeacherSp());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return FilteredData;
    }

    private Filter FilteredData=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TeacherDetailInformationData> newList=new ArrayList<>(  );
            if(charSequence == null|| charSequence.length() == 0){
                newList.addAll(templist);
            }else {
                String filterpattern=charSequence.toString().toLowerCase().trim();
                for (TeacherDetailInformationData item : templist){
                    if(item.getTeacherName().toLowerCase().contains( filterpattern ) ||item.getTeacherSp().toLowerCase().contains( filterpattern ) ||item.getTeacherMobile().toLowerCase().contains(filterpattern)){
                        newList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=newList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            teacherDetailInformationData.clear();
            teacherDetailInformationData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
