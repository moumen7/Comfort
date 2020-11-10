package com.example.myapplication.Note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.NoCopySpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText editTexttitle;
    private EditText editText;
    private ImageView imageView;
    Context context;

    private EditText editTextdescription;

    public static final String EXTRA_BArray = "S" ;

    public static final String EXTRA_ID =
            "20";
    public static final String EXTRA_TITLE =
            "Moumen";
    public static final String EXTRA_DESCRIPTION =
            "Moumen tany";
    public static final String EXTRA_PRIORITY =
            "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        imageView = (ImageView) findViewById(R.id.my_avatar);
        editTexttitle = findViewById(R.id.edit_text_title);
        editTextdescription = findViewById(R.id.edit_text_description);
        editText = findViewById(R.id.textView2);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#620d86"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("EDIT NOTE");
            editTexttitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextdescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editText.setText(intent.getStringExtra(EXTRA_PRIORITY));
            Bitmap bitmap = BitmapFactory.decodeByteArray(intent.getByteArrayExtra(EXTRA_BArray), 0, intent.getByteArrayExtra(EXTRA_BArray).length);
            imageView.setImageBitmap(bitmap);
        }
        else {
            setTitle("ADD NOTE");
        }

    }
    public void clickNew(View v)
    {
        selectImage(this);
    }
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery" ,"Delete Picture" , "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                    pickPhoto.setType("image/*");
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Delete Picture")) {
                    dialog.dismiss();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {

                        try {
                            InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
                            imageView.setImageBitmap(bitmap1);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                    }
                    break;
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnotemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void saveNote()
    {
        String title = editTexttitle.getText().toString();
        String desc = editTextdescription.getText().toString();
        String p = editText.getText().toString();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);

        byte[] bytes =  stream.toByteArray();

        if(title.trim().isEmpty() || desc.trim().isEmpty())
        {
            Toast.makeText(this, "Invalid" , Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, desc);
        data.putExtra(EXTRA_BArray, bytes);
        data.putExtra(EXTRA_PRIORITY, p);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        Toast.makeText(this, p + "kkkkk", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, data);
        finish();
    }
}
