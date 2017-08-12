# Task_2_android
second task

first of all, I used Viewpager and Tablayout to implement tabs in my app. For this I had to implement an adapter called my pager adapter in main activity.


	public class MyPagerAdapter extends FragmentPagerAdapter
	{
        public MyPagerAdapter(FragmentManager fragmentManager) 
		{
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }
	
Then on those two tabs I implemented two diffrent fragments,
One is to call the list from internal storage called Show_list.java
	
	
	@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<data> arrayList = new ArrayList<data>();
        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures/Camera_App";
        File mediaStorageDir = new File("/sdcard/Pictures/Camera_App");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
            }
        }
        File f = new File(path);
        File file[] = f.listFiles();
        Log.e("FOLDER", "New folder");
        for(int i = 0; i < file.length; i++) {

            //System.out.println(" "+file[i]);
            String str=file[i].toString();
            int a = str.lastIndexOf('/')+1;
            int b = str.length();
            String sub=str.substring(a,b);
            arrayList.add(new data(sub));
            Log.e("FOLDER", ""+sub);
        }
        ListView listView = (ListView) view.findViewById(R.id.list);
        data_adapter adapter = new data_adapter(getActivity(),arrayList);
        listView.setAdapter(adapter);
    }
	
ANd another fragment is Action.java

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.action_page, container, false);

        Button captureButton = (Button) rootView.findViewById(R.id.pic_click);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getActivity(), To_click.class);
                        startActivity(i);
                        Log.e("fuck","fuck");
                    }
                }
        );
        return rootView;
    }
	
To implement camera view inside the app I took reference from "https://developer.android.com/guide/topics/media/camera.html" and start a new activity To_click.java and implemented store the pictues in internal storage.  

Then I implement a form on a dialog box.
At last to print the graph I implement a java file/package LowHighGraph.java by using AppCompatImageView class, Canvas for graph, Paint to color it and path to track tha cordinates.

	public class LowHighGraph extends android.support.v7.widget.AppCompatImageView 
	{
    	private double percentage;

    private final static String TAG = LowHighGraph.class.getSimpleName();

    public LowHighGraph(Context context) {
        super(context);
        init(context);
    }

    public LowHighGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init(attrs);
    }

    public LowHighGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        init(attrs);
    }

    private void init(Context context) {
        this.percentage = 0.9;
        invalidate();
    }

    private void init(AttributeSet attrs) {
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
        invalidate();
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0, height/2, width, height/2, Color.GREEN, Color.RED, Shader.TileMode.MIRROR));
        paint.setAntiAlias(true);

        PointF p1 = new PointF(0, height);
        float x = (float) (percentage * width);
        PointF p2 = new PointF(x, height);

        float y = (width * height - height * x) / (float) width;
        PointF p3 = new PointF(x, y);

        Log.e(TAG, p1.toString());
        Log.e(TAG, p2.toString());
        Log.e(TAG, p3.toString());

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.reset();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();

        canvas.drawPath(path, paint);
    }
}

and the used it in my view.

	<com.app.task_2_android_app.LowHighGraph
        android:layout_width="match_parent"
        android:layout_height="fill_parent"
        android:background="#ffffff"
        android:layout_above="@+id/button"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        />
