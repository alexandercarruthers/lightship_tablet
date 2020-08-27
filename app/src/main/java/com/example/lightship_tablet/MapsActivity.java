package com.example.lightship_tablet;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int TRANSPARENCY_MAX = 100;

    /** This returns moon tiles. */
    private static final String MOON_MAP_URL_FORMAT =
            "https://mw1.google.com/mw-planetary/lunar/lunarmaps_v1/clem_bw/%d/%d/%d.jpg";
    private static final String MAP_URL =
            "http://10.0.2.2:8000/media/decks/DECK1-1_yDzBpAA.png";
    private TileOverlay moonTiles;
    private SeekBar transparencyBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////
////        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
////    }
    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_NONE);

        TileProvider tileProvider = new UrlTileProvider(1, 1) {
            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                // The moon tile coordinate system is reversed.  This is not normal.
                //int reversedY = (1 << zoom) - y - 1;
                //String s = String.format(Locale.US, MAP_URL, zoom, x, reversedY);
                URL url = null;
                try {
                    url = new URL(MAP_URL);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
                return url;
            }
        };

        moonTiles = map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));

    }
}