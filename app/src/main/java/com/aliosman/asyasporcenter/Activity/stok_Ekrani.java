package com.aliosman.asyasporcenter.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeNoticeDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.aliosman.asyasporcenter.Adapter.CustomDialog;
import com.aliosman.asyasporcenter.Adapter.Helper.ReyclerItemTouchHelper;
import com.aliosman.asyasporcenter.Adapter.Helper.ReyclerItemTouchHelperListener;
import com.aliosman.asyasporcenter.Adapter.adapter_stok;
import com.aliosman.asyasporcenter.Connection.IListUrun;
import com.aliosman.asyasporcenter.Connection.UrunConnection;
import com.aliosman.asyasporcenter.Fragment.ImageCrop;
import com.aliosman.asyasporcenter.Fragment.stok_add_bottom;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.IAddBottomClick;
import com.aliosman.asyasporcenter.Values.IRecylerItemClick;
import com.aliosman.asyasporcenter.Values.ISendImage;
import com.aliosman.asyasporcenter.Values.Urun;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class stok_Ekrani extends AppCompatActivity implements IRecylerItemClick,ISendImage, ReyclerItemTouchHelperListener {
    String TAG = getClass().getName();
    private TextView txt_toplam;
    private RecyclerView recyclerView;
    private SearchView search;
    private Dialog loading;
    private adapter_stok adapter;
    private List<Urun> uruns;
    private final int REQUEST_STORAGE = 0;
    private final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;
    private Uri cameraImageUri = null;
    private BottomSheetLayout bottomSheetLayout;
    private stok_add_bottom bottom_dialog;
    private Bundle saveBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        setContentView(R.layout.activity_stok_ekrani);
        txt_toplam=findViewById(R.id.txt_toplam);
        Button btn_ekle = findViewById(R.id.layout_stok_UrunEkle);
        recyclerView = findViewById(R.id.layout_stok_Recycler);
        btn_ekle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("click",click);
                bottom_dialog =new stok_add_bottom();
                bottom_dialog.setArguments(args);
                bottom_dialog.show(getSupportFragmentManager(), "urun_ekle");
            }
        });
        RefreshList();
        bottomSheetLayout=findViewById(R.id.bottomSheet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        ItemTouchHelper.SimpleCallback item = new ReyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(item).attachToRecyclerView(recyclerView);

        loading=new CustomDialog().Loading(this);
        loading.show();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause: " );
        super.onPause();
        if (bottom_dialog!=null)
            bottom_dialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        search = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        search.setQueryHint("Ürün Arama...");
        search.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            public boolean onQueryTextChange(String s) { ;
                if (s.length() != 0) {
                    SetAdapter(SearchList(s));
                } else {
                    SetAdapter(uruns);
                }
                return false;
            }
        });
        return true;
    }

    private List<Urun> SearchList(String s) {
        List<Urun> temp = new ArrayList();
        for (Urun item : uruns) {
            if (item.getUrunAdi().toLowerCase().contains(s)) {
                temp.add(item);
            }
        }
        return temp;
    }

    private void SetAdapter(List<Urun> uruns) {
        int toplam=0;
        for (Urun item:uruns) {
            toplam+=(item.getCount40()+item.getCount41()+item.getCount42()+item.getCount43()+item.getCount44()+item.getCount45());
        }
         if (loading!=null && loading.isShowing())
            loading.dismiss();
        adapter=new adapter_stok(uruns,this);
        this.recyclerView.setAdapter(adapter);
        if (txt_toplam!=null)
            txt_toplam.setText(toplam+" ");
    }

    @Override
    public void Click(Urun urun) {
        bottom_dialog = new stok_add_bottom();
        Bundle args = new Bundle();
        args.putSerializable("Urun", urun);
        args.putSerializable("click",click);
        bottom_dialog.setArguments(args);
        bottom_dialog.show(getSupportFragmentManager(), "urun_select");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;
    }

    private IAddBottomClick click=new IAddBottomClick() {
        @Override
        public void Click() {
            SaveAndCloseDialog();
            showSheetView();
        }
    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        cameraImageUri = FileProvider.getUriForFile(getBaseContext(), getApplicationContext().getPackageName(), imageFile);//Uri.fromFile(imageFile);
        return imageFile;
    }

    @Nullable
    private Intent createPickIntent() {
        Intent picImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (picImageIntent.resolveActivity(getPackageManager()) != null) {
            return picImageIntent;
        } else {
            return null;
        }
    }

    @Nullable
    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = createCameraIntent();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent != null) {
            // Create the File where the photo should go
            try {
                File imageFile = createImageFile();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                Log.e(TAG, "dispatchTakePictureIntent: "+e.getMessage() );
                e.printStackTrace();
                // Error occurred while creating the File
            }
        }
    }

    private boolean checkNeedsPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ActivityCompat.checkSelfPermission(stok_Ekrani.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission denied
                Toast.makeText(this, "Sheet is useless without access to external storage :/", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /***
     * Tab 4 Resim Yükleme Hatalı
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: "+(requestCode));
        if (requestCode!=Activity.RESULT_CANCELED){
            Uri selectedImage=null;
            Log.e(TAG, "onActivityResult: "+(data==null) );
            if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                selectedImage = data.getData();
                Log.e(TAG, "onActivityResult: "+ selectedImage.toString());
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath
                Log.e(TAG, "onActivityResult: Resim Çekildi" );
                selectedImage = cameraImageUri;
            }

            if (selectedImage != null) {
                Log.e(TAG, "onActivityResult: not null" );
                showSelectedImage(selectedImage);
                //showSelectedImage(selectedImage);
            } else {

            }
        }
    }

    private void showSheetView() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(this)
                .setMaxItems(30)
                .setShowCameraOption(createCameraIntent() != null)
                .setShowPickerOption(createPickIntent() != null)
                .setImageProvider(new ImagePickerSheetView.ImageProvider() {
                    @Override
                    public void onProvideImage(ImageView imageView, Uri imageUri, int size) {
                        Picasso.get()
                                .load(imageUri)
                                .centerCrop()
                                .fit()
                                .into(imageView);
                    }
                })
                .setOnTileSelectedListener(new ImagePickerSheetView.OnTileSelectedListener() {
                    @Override
                    public void onTileSelected(ImagePickerSheetView.ImagePickerTile selectedTile) {
                        bottomSheetLayout.dismissSheet();
                        if (selectedTile.isCameraTile()) {
                            SaveAndCloseDialog();
                            dispatchTakePictureIntent();
                        } else if (selectedTile.isPickerTile()) {
                            SaveAndCloseDialog();
                            startActivityForResult(createPickIntent(), REQUEST_LOAD_IMAGE);
                        } else if (selectedTile.isImageTile()) {
                            showSelectedImage(selectedTile.getImageUri());
                        } else {

                        }
                    }
                })
                .setTitle("Choose an image...")
                .create();

        bottomSheetLayout.showWithSheetView(sheetView);
    }
    private void showSelectedImage(Uri selectedImageUri) {
        Log.e(TAG, "showSelectedImage: ");
        final ImageCrop fr = new ImageCrop();
        final Bundle args  = new Bundle();
        args.putSerializable("send",(ISendImage)this);
        args.putParcelable("uri",selectedImageUri);
        fr.setArguments(args);
        fr.show(getSupportFragmentManager(),"deneme");
    }
    private void SaveAndCloseDialog(){
        Log.e(TAG, "SaveAndCloseDialog: "+(bottom_dialog==null) );
        saveBottom=bottom_dialog.onSaveInstanceState();
        Log.e(TAG, "SaveAndCloseDialog: "+(saveBottom.getSerializable("newUrUn")==null) );
        bottom_dialog.dismiss();
    }
    private void OpenDialog(){
        Bundle args = new Bundle();
        args.putSerializable("click",click);
        bottom_dialog =new stok_add_bottom();
        bottom_dialog.setArguments(args);

        bottom_dialog.showNow(getSupportFragmentManager(), "urun_ekle");
        bottom_dialog.onViewStateRestored(saveBottom,true);
    }
    private void RefreshList(){
        if (loading!=null && loading.isShowing())
            loading.dismiss();
        new UrunConnection().ListUrun(new IListUrun() {
            @Override
            public void ListUrun(List<Urun> urunler) {
                uruns=urunler;
                if (search!=null &&search.getQuery().length()!=0){
                    SearchList(search.getQuery().toString());
                }else{
                    SetAdapter(uruns);
                }
            }
        });
    }
    @Override
    public void SendBitmap(Bitmap image) {
        OpenDialog();
        bottom_dialog.SendImage(image);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        final Urun item = adapter.getItem(position);
        new AwesomeInfoDialog(this)
                .setPositiveButtonText("Evet")
                .setNegativeButtonText("Hayır")
                .setNegativeButtonTextColor(R.color.colorWhite)
                .setPositiveButtonbackgroundColor(R.color.colorRed)
                .setNegativeButtonbackgroundColor(R.color.colorHeader)
                .setTitle("Uyarı")
                .setMessage("Geçerli Ürünü Silmek istediğinizden eminmisiniz?")
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        adapter.removeItem(position);
                        Log.e(TAG, "exec: Sil" );
                        new UrunConnection().DeleteUrun(item);
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        Log.e(TAG, "exec: Silme" );
                        adapter.notifyDataSetChanged();
                    }
                }).show();
    }
}