package com.example.diplom.QR;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScanFragment extends Fragment {

    private FirebaseAuth auth;
    private Button btn_scan;
    private RecyclerView recyclerView;
    private List<String> scannedDataList;
    private ScanDataAdapter adapter;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        db = FirebaseFirestore.getInstance();

        btn_scan = view.findViewById(R.id.btn_scan);
        recyclerView = view.findViewById(R.id.recycler_view);
        scannedDataList = new ArrayList<>();
        adapter = new ScanDataAdapter(scannedDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btn_scan.setOnClickListener(v -> {
            scanCode();
        });

        return view;
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String barcode = result.getContents();
            new Thread(() -> {
                String productTitle = getProductTitle(barcode);
                requireActivity().runOnUiThread(() -> {
                    if (productTitle != null) {
                        scannedDataList.add(productTitle);
                        adapter.notifyDataSetChanged();
                        saveToFirestore(productTitle);
                    }
                    showResultDialog(productTitle);
                });
            }).start();
        }
    });

    private String getProductTitle(String barcode) {
        try {
            String url = "https://barcode-list.ru/barcode/RU/Поиск.htm?barcode=" + barcode;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Pattern pattern = Pattern.compile("<title>(.*?)</title>");
            Matcher matcher = pattern.matcher(response.toString());
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveToFirestore(String productTitle) {
        auth = FirebaseAuth.getInstance();
        String prod = "products" +":"+ auth.getCurrentUser();
        db.collection(prod)
                .add(new HashMap<String, Object>() {{
                    put("title", productTitle);
                    put("timestamp", FieldValue.serverTimestamp());
                }})
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Product added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding product", e));
    }

    private void showResultDialog(String productTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Result");
        builder.setMessage(productTitle != null ? productTitle : "Product title not found");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        startListeningToFirestore();
    }

    private void startListeningToFirestore() {
        auth = FirebaseAuth.getInstance();
        String prod = "products" +":"+ auth.getCurrentUser();
        db.collection(prod)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(TAG, "Listen failed: " + error);
                        return;
                    }

                    scannedDataList.clear();

                    for (QueryDocumentSnapshot doc : value) {
                        String productTitle = doc.getString("title");
                        scannedDataList.add(productTitle);
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}