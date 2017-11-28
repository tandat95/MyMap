package com.vbd.mapexam.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vbd.mapexam.R;
import com.vbd.mapexam.adapter.RecyclerViewGuideStreetAdapter;
import com.vbd.mapexam.adapter.RecyclerViewPointSavedAdapter;
import com.vbd.mapexam.adapter.RecyclerViewResultAdapter;
import com.vbd.mapexam.adapter.RecyclerViewStreetSavedAdapter;
import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.listener.BtnEditOnclickCallBack;
import com.vbd.mapexam.listener.BtnSaveStreetOnclickCallback;
import com.vbd.mapexam.listener.BtnToHereOnclickCallback;
import com.vbd.mapexam.listener.FindShortPathCallback;
import com.vbd.mapexam.listener.GetAllPointSavedCallback;
import com.vbd.mapexam.listener.GetAllStreetSavedCallback;
import com.vbd.mapexam.listener.GuidStreetItemClickCallback;
import com.vbd.mapexam.listener.InsertStreetCallback;
import com.vbd.mapexam.listener.SearchPointCallback;
import com.vbd.mapexam.listener.StreetSavedItemClickCallback;
import com.vbd.mapexam.listener.UpdatePointCallback;
import com.vbd.mapexam.model.PointSaved;
import com.vbd.mapexam.model.SearchResultObj;
import com.vbd.mapexam.model.ShortPathObj;
import com.vbd.mapexam.model.StreetSaved;
import com.vbd.mapexam.presenter.FindShortPath;
import com.vbd.mapexam.presenter.GetAllPointSaved;
import com.vbd.mapexam.presenter.GetAllStreetSaved;
import com.vbd.mapexam.presenter.InsertStreet;
import com.vbd.mapexam.presenter.SearchPoint;
import com.vbd.mapexam.presenter.UpdatePoint;
import com.vbd.mapexam.view.fragment.BottomGuidStreetFragment;
import com.vbd.mapexam.view.fragment.BottomPointSavedFragment;
import com.vbd.mapexam.view.fragment.BottomResultFragment;
import com.vbd.mapexam.view.fragment.BottomStreetSavedFragment;
import com.vbd.mapexam.view.fragment.FindStreetFragment;
import com.vbd.mapexam.view.fragment.SearchPointFragment;
import com.vbd.mapexam.view.fragment.VbdMapFragment;
import com.vietbando.vietbandosdk.annotations.Icon;
import com.vietbando.vietbandosdk.annotations.IconFactory;
import com.vietbando.vietbandosdk.annotations.Marker;
import com.vietbando.vietbandosdk.annotations.MarkerOptions;
import com.vietbando.vietbandosdk.annotations.PolylineOptions;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;
import static com.vbd.mapexam.view.fragment.VbdMapFragment.map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {


    @SuppressLint("StaticFieldLeak")
    public static MaterialSearchBar searchBar;
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progressBar;
    private DrawerLayout drawer;
    private BottomSheetDialog dialogSearchResult;
    @SuppressLint("StaticFieldLeak")
    public static Button btnShow;
    public static FragmentManager fm;
    @SuppressLint("StaticFieldLeak")
    public static FloatingActionButton fabFindStreet, fabMyLocation;
    public static FragmentTransaction ftAddMap, ftAddSearchPoint, ftAddFindStreet;
    @SuppressLint("StaticFieldLeak")
    public static Fragment fmMap, fmStreet, fmPoint;
    public static BottomSheetDialogFragment bottomSearchResult;
    public static BottomSheetDialogFragment bottomPointSaved;
    public static BottomSheetDialogFragment bottomGuideStreet;
    public static BottomSheetDialogFragment bottomStreetSaved;
    private TextView fullUserName;
    private TextView userEmail;
    private MenuItem nav_logout;
    public static int CODE_SEARCH = 0;
    public static LatLng listPoint[] = new LatLng[2];
    public static String listNamePoint[] = new String[2];
    public static LatLng mylocation;
    public static Icon iconMylocation;
    LocationManager locationManager;
    Double latitude, longitude;
    SharedPreferences userInfoPf;
    Marker markerMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShow = (Button) findViewById(R.id.button_showDialog);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_search);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);
        navigationView.addHeaderView(headerView);

        Menu menu = navigationView.getMenu();
        nav_logout = menu.findItem(R.id.nav_logout);

        fullUserName = (TextView) headerView.findViewById(R.id.textView_fullUserName);
        userEmail = (TextView) headerView.findViewById(R.id.textView_userEmail);

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE_SEARCH = 0;
                map.clear();
                getSearchPointFragment();
            }
        });

        //create fragment
        fmMap = new VbdMapFragment();
        fmPoint = new SearchPointFragment();
        fmStreet = new FindStreetFragment();

        //create floatingActionButton
        fabFindStreet = (FloatingActionButton) findViewById(R.id.fab_FindStreet);
        fabMyLocation = (FloatingActionButton) findViewById(R.id.fab_MyLocation);

        //load mapfragment when app start
        fm = getFragmentManager();
        getMapFragment();

        //on floatingActionButton FindStreet click
        fabFindStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFindStreetFragment(MainActivity.this);
            }
        });
        //on floatingActionButton Mylocation click


        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map!=null) {
                    map.clear();
                    map.addMarker(new MarkerOptions().position(mylocation).icon(iconMylocation));
                    map.easeCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
                }
                btnShow.setVisibility(View.GONE);
            }
        });

        //define userInfoPf.xml
        userInfoPf = getSharedPreferences(Config.USER_INFO_RF_NAME, MODE_PRIVATE);
    }

    //on android back button press
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (searchBar.isSearchEnabled()) {
            searchBar.disableSearch();
        }
        cleanLayout();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //on navigation item select
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        boolean loginSuccess = userInfoPf.getBoolean("login_success", false);
        int id = item.getItemId();

        if (id == R.id.nav_findStreet) {

            getFindStreetFragment(MainActivity.this);


        } else if (id == R.id.nav_favoriteLocation) {

            if (loginSuccess) {
                createDialogPointSaved(userInfoPf, MainActivity.this);
            } else {
                Toast.makeText(MainActivity.this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_savedStreet) {

            if (loginSuccess) {
                createDialogStreetSaved(userInfoPf, MainActivity.this);
            } else {
                Toast.makeText(MainActivity.this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_logout) {
            logOutItemClick();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //on search state changed
    @Override
    public void onSearchStateChanged(boolean enabled) {
        btnShow.setVisibility(View.GONE);
    }

    //on search confirm
    @Override
    public void onSearchConfirmed(CharSequence text) {

        fm.popBackStack("fMap", 0);
        backToMapFragment();
        createDialogSearchResult(text.toString(), MainActivity.this, userInfoPf);

    }

    //create bottom sheet view
    public void createDialogSearchResult(String keyword, final Context context, final SharedPreferences userInfoPf) {

        final SearchPoint searchPoint;
        searchPoint = (SearchPoint) new SearchPoint(keyword, context, new SearchPointCallback() {
            @Override
            public void onSearchFinish(final ArrayList<SearchResultObj> searchResultObjArrayList) {

                if (searchResultObjArrayList != null) {
                    RecyclerViewResultAdapter adapter = new RecyclerViewResultAdapter(searchResultObjArrayList,
                            MainActivity.this, userInfoPf, new GuidStreetItemClickCallback() {
                        @Override
                        public void onItemClicked(int position) {
                            if (CODE_SEARCH != 0) {
                                if (listPoint[0] != null && listPoint[1] != null) {
                                    ArrayList<LatLng> arrayList = new ArrayList<>();
                                    arrayList.add(listPoint[0]);
                                    arrayList.add(listPoint[1]);
                                    createDialogGuidStreet(arrayList, context);
                                }
                            }
                        }
                    }, new BtnToHereOnclickCallback() {
                        @Override
                        public void onBtnTohereClick(LatLng latLng, String name) {
                            listPoint[0] = mylocation;
                            listPoint[1] =latLng;
                            bottomSearchResult.dismiss();
                            getFindStreetFragment(context);
                            ArrayList<LatLng> arrayList = new ArrayList<>();
                            arrayList.add(listPoint[0]);
                            arrayList.add(listPoint[1]);
                            createDialogGuidStreet(arrayList, context);
                            CODE_SEARCH = 2;
                            Bundle bundle = new Bundle();
                            bundle.putString("location_name", name);
                            fmStreet.setArguments(bundle);
                        }
                    });
                    bottomSearchResult = new BottomResultFragment(adapter);
                    bottomSearchResult.show(getSupportFragmentManager(), "Bottom sheet search result");
                }
            }
        }).execute();
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSearchResult.show(getSupportFragmentManager(), "Bottom sheet search result");
            }
        });
//
    }

    public void createDialogPointSaved(final SharedPreferences sharedPreferences, final Context context) {

        GetAllPointSaved getAllPointSaved = (GetAllPointSaved) new GetAllPointSaved(sharedPreferences, context, new GetAllPointSavedCallback() {
            @Override
            public void onGetAllPointSavedFinish(ArrayList<PointSaved> pointSavedList) {
                RecyclerViewPointSavedAdapter adapter = new RecyclerViewPointSavedAdapter(pointSavedList, sharedPreferences, context, new BtnEditOnclickCallBack() {
                    @Override
                    public void onBtnEditClick(final String id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        View view = getLayoutInflater().inflate(R.layout.edit_point, null);
                        final EditText edtName = (EditText) view.findViewById(R.id.editTexr_namePoint);
                        final EditText edtAdress = (EditText) view.findViewById(R.id.editTexr_adress);
                        final EditText edtInfo = (EditText) view.findViewById(R.id.editTexr_info);
                        Button btnUpdate = (Button) view.findViewById(R.id.button_update);
                        Button btnCancel = (Button) view.findViewById(R.id.button_cancel);
                        builder.setView(view);
                        builder.setTitle("Cập nhật thông tin");
                        builder.setCancelable(true);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onClick(View v) {
                                String name = edtName.getText().toString();
                                String adress = edtAdress.getText().toString();
                                String info = edtInfo.getText().toString();
                                new UpdatePoint(id, name, adress, info, userInfoPf, context, new UpdatePointCallback() {
                                    @Override
                                    public void onUpdateFinish(Boolean success) {
                                        if (success) {
                                            alertDialog.dismiss();
                                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Lỗi kết nối máy chủ, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).execute();
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });
                    }
                });

                bottomPointSaved = new BottomPointSavedFragment(adapter);
                bottomPointSaved.show(getSupportFragmentManager(), "Bottom sheet point saved");
            }
        }).execute();
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomPointSaved.show(getSupportFragmentManager(), "Bottom sheet point saved");
            }
        });
    }

    public void createDialogGuidStreet(ArrayList<LatLng> arrayList, final Context context) {
        int type = 1;
        new FindShortPath(arrayList, type, context, new FindShortPathCallback() {
            @Override
            public void onFindShortPathFinish(ShortPathObj shortPathObj) {
                if (shortPathObj == null) {
                    Toast.makeText(context, "Vui lòng chọn khoảng cách gần hơn", Toast.LENGTH_LONG).show();
                } else {
                    if (shortPathObj.getFullPath() != null) {
                        //get list fullpath
                        final List<Double> listFullPath = shortPathObj.getFullPath().get(0).get(0);
                        // get list Step
                        List<ShortPathObj.Leg.Step> step = shortPathObj.getResultScript().getLeg().get(0).getStep();
//                    List<String> directionForm = Arrays.asList(" Đi thẳng ", " Rẽ trái vào ", " Rẽ phải vào ", " Đi tiếp ", " Qua ", " Rẽ vào ", " Đến ");
//
//                    for (int i = 0; i < step.size(); i++) {
//                        ShortPathObj.Leg.Step stepObj = step.get(i);
//                        String name = stepObj.getName();
//                        if (name.equals("")) {
//                            name = "Đường không tên";
//                        }
//                        String dir = stepObj.getDir();
//                        int start = stepObj.getStart();
//                        float len = stepObj.getLen();
//                        String unit = "m";
//                        if (len >= 500) {
//                            len = len / 1000;
//                            len = (float) ((double) Math.round(len * 100) / 100);
//                            unit = "km";
//                        }
//                        String guide = directionForm.get(parseInt(dir)) + name;
//
//                        Log.d("leng", guide + " " + len + unit);
//
                        final RecyclerViewGuideStreetAdapter adapter = new RecyclerViewGuideStreetAdapter(step, new GuidStreetItemClickCallback() {
                            @Override
                            public void onItemClicked(final int position) {
                                @SuppressLint("StaticFieldLeak")
                                AsyncTask<Void, Void, ArrayList<LatLng>> task = new AsyncTask<Void, Void, ArrayList<LatLng>>() {
                                    @Override
                                    protected ArrayList<LatLng> doInBackground(Void... voids) {
                                        //create list point from list fullpath
                                        ArrayList<LatLng> points = new ArrayList<>();
                                        for (int i = 0; i < listFullPath.size(); i = i + 2) {
                                            LatLng latLng = new LatLng(listFullPath.get(i + 1), listFullPath.get(i));
                                            points.add(latLng);
                                        }
                                        return points;
                                    }

                                    @Override
                                    protected void onPostExecute(ArrayList<LatLng> latLngs) {
                                        super.onPostExecute(latLngs);

                                        List<Marker> markerList = map.getMarkers();
                                        if (markerList.size() > 2) {
                                            Marker markerPrevious = markerList.get(markerList.size() - 1);
                                            map.removeMarker(markerPrevious);
                                        }
                                        Icon icon = IconFactory.getInstance(MainActivity.this).fromResource(R.drawable.ic_curent_location);
                                        map.addMarker(new MarkerOptions().position(latLngs.get(position)).setIcon(icon));
                                        map.easeCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(position), 14), 500);
                                    }
                                };
                                task.execute();
                                bottomGuideStreet.dismiss();
                                btnShow.setVisibility(View.VISIBLE);
                            }
                        });
                        bottomGuideStreet = new BottomGuidStreetFragment(adapter, new BtnSaveStreetOnclickCallback() {
                            @Override
                            public void onBtnSaveStreetClick() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                View view = getLayoutInflater().inflate(R.layout.save_street, null);
                                final EditText edtPathName = (EditText) view.findViewById(R.id.editTexr_pathName);
                                Button btnSave = (Button) view.findViewById(R.id.button_saveStreetDialog);
                                Button btnCancel = (Button) view.findViewById(R.id.button_cancelSaveStreet);
                                builder.setTitle("Nhập tên lộ trình");
                                builder.setView(view);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String pathName = edtPathName.getText().toString();

                                        new InsertStreet(pathName, listPoint, listNamePoint, userInfoPf, new InsertStreetCallback() {
                                            @Override
                                            public void onInsertFinish(Boolean success) {
                                                if (success) {
                                                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(context, "Lỗi kết nối, thử lại sau!", Toast.LENGTH_SHORT).show();
                                                }
                                                alertDialog.cancel();
                                            }
                                        }).execute();
                                    }
                                });
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.cancel();
                                    }
                                });
                            }
                        });
                        bottomGuideStreet.show(getSupportFragmentManager(), "Bottom sheet guide street");
                        drawLine(listFullPath);
                        btnShow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomGuideStreet.show(getSupportFragmentManager(), "Bottom sheet guide street");
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Service Error!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).execute();

    }

    public void createDialogStreetSaved(final SharedPreferences userInfoPf, final Context context) {
        new GetAllStreetSaved(userInfoPf, context, new GetAllStreetSavedCallback() {
            @Override
            public void onGetStreetFinish(ArrayList<StreetSaved> listStreetSaved) {
                RecyclerViewStreetSavedAdapter adapter = new RecyclerViewStreetSavedAdapter(listStreetSaved, context, userInfoPf, new StreetSavedItemClickCallback() {
                    @Override
                    public void onItemClick(ArrayList<LatLng> listLatlng) {
                        bottomStreetSaved.dismiss();
                        btnShow.setText("Xem chỉ dẫn");
                        createDialogGuidStreet(listLatlng, context);
                    }
                });
                bottomStreetSaved = new BottomStreetSavedFragment(adapter);
                bottomStreetSaved.show(getSupportFragmentManager(), "Bottom sheet street saved");
                btnShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomStreetSaved.show(getSupportFragmentManager(), "Bottom sheet street saved");
                    }
                });
            }
        }).execute();
    }

    //on search bar button click
    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;

            case MaterialSearchBar.BUTTON_BACK:
                onBackPress();
                break;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    //on MainActivity resume
    @Override
    protected void onResume() {
        //check login
        SharedPreferences getUserInfoPf = getSharedPreferences("user_info", MODE_PRIVATE);
        String user = getUserInfoPf.getString("user_name", "");
        String email = getUserInfoPf.getString("email", "");
        if (!user.equals("") && !email.equals("")) {
            fullUserName.setText(user);
            userEmail.setText(email);
            nav_logout.setTitle("Đăng xuất");
        }
        getLocation();
        super.onResume();
    }

    //on button back press
    public void onBackPress() {
        fm.popBackStack();
        cleanLayout();
    }

    //remove old view when change fragment
    public void cleanLayout() {
        Fragment streetFragment = getFragmentManager().findFragmentByTag("fStreet");
        Fragment mapFragment = getFragmentManager().findFragmentByTag("fMap");
        if (streetFragment != null && streetFragment.isVisible()) {
            backToFindStreetFragment(MainActivity.this);
        } else if (mapFragment != null && mapFragment.isVisible()) {
            backToMapFragment();
        }
    }

    //modul of cleanlayout function
    public static void backToMapFragment() {
        if (searchBar.isSearchEnabled()) {
            searchBar.disableSearch();
        }
        searchBar.setVisibility(View.VISIBLE);
        fabFindStreet.setVisibility(View.VISIBLE);
        fabMyLocation.setVisibility(View.VISIBLE);

    }

    public static void backToFindStreetFragment(Context context) {

        searchBar.setVisibility(View.INVISIBLE);
        fabFindStreet.setVisibility(View.INVISIBLE);
        fabMyLocation.setVisibility(View.INVISIBLE);
        btnShow.setVisibility(View.INVISIBLE);
    }

    public static void backToSearchPointFragment() {
        searchBar.setVisibility(View.VISIBLE);
        fabFindStreet.setVisibility(View.INVISIBLE);
        fabMyLocation.setVisibility(View.INVISIBLE);

    }

    //get fragment
    public static void getMapFragment() {

        ftAddMap = fm.beginTransaction();
        ftAddMap.addToBackStack("fMap");
        ftAddMap.add(R.id.fragmentContainer, fmMap, "fMap");
        ftAddMap.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ftAddMap.commit();
        backToMapFragment();
    }

    @SuppressLint("ResourceType")
    public static void getFindStreetFragment(Context context) {

        ftAddFindStreet = fm.beginTransaction();
        ftAddFindStreet.addToBackStack(null);
        ftAddFindStreet.setCustomAnimations(R.anim.enter_from_top, R.anim.out_to_top);
        ftAddFindStreet.add(R.id.fragmentContainer, fmStreet, "fStreet");

        //ftAddFindStreet.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ftAddFindStreet.addToBackStack(null);
        ftAddFindStreet.commit();
        backToFindStreetFragment(context);
    }

    public static void getSearchPointFragment() {
        if (!searchBar.isSearchEnabled()) {
            searchBar.enableSearch();
        }

        ftAddSearchPoint = fm.beginTransaction();
        ftAddSearchPoint.addToBackStack("fPoint");
        ftAddSearchPoint.add(R.id.fragmentContainer, fmPoint, "fPoint");
        ftAddSearchPoint.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ftAddSearchPoint.addToBackStack(null);
        ftAddSearchPoint.commit();
        backToSearchPointFragment();
    }

    //on logout button click
    public void logOutItemClick() {
        final SharedPreferences getUserInfoPf = getSharedPreferences("user_info", MODE_PRIVATE);
        boolean loginSuccess = getUserInfoPf.getBoolean("login_success", false);
        if (loginSuccess) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Đăng xuất?")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = getUserInfoPf.edit();
                            editor.clear();
                            editor.putBoolean("login_success", false);
                            editor.apply();
                            nav_logout.setTitle("Đăng nhập");
                            fullUserName.setText(R.string.full_name);
                            userEmail.setText(R.string.email);
                            btnShow.setVisibility(View.GONE);
                            map.clear();
                            Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    //draw polyline on map
    private void drawLine(final List<Double> listFullPath) {

        @SuppressLint("StaticFieldLeak")

        AsyncTask<Void, Void, ArrayList<LatLng>> task = new AsyncTask<Void, Void, ArrayList<LatLng>>() {
            @Override
            protected ArrayList<LatLng> doInBackground(Void... voids) {
                ArrayList<LatLng> points = new ArrayList<>();
                for (int i = 0; i < listFullPath.size(); i = i + 2) {
                    LatLng latLng = new LatLng(listFullPath.get(i + 1), listFullPath.get(i));
                    points.add(latLng);
                }
                return points;
            }

            @Override
            protected void onPostExecute(ArrayList<LatLng> latLngs) {
                super.onPostExecute(latLngs);

                Icon startIcon = IconFactory.getInstance(MainActivity.this).fromResource(R.drawable.ic_start);

                LatLng[] pointsArray = latLngs.toArray(new LatLng[latLngs.size()]);
                map.clear();
                map.addMarker(new MarkerOptions().position(pointsArray[0])).setIcon(startIcon);
                map.addMarker(new MarkerOptions().position(pointsArray[pointsArray.length - 1]));
                map.addPolyline(new PolylineOptions()
                        .add(pointsArray)
                        .color(parseColor("#CAB60000"))
                        .width(4));
                map.easeCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 11));
            }
        };

        task.execute();

    }

    //get mylocation
    public void getLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
        } else {
            Toast.makeText(getApplicationContext(), "Lỗi định vi", Toast.LENGTH_LONG).show();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 30, new MyLocationListener());

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mylocation = new LatLng(latitude, longitude);
            listPoint[0] = mylocation;

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, "GPS ON", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "GPS OFF:", Toast.LENGTH_SHORT).show();
        }
    }
}