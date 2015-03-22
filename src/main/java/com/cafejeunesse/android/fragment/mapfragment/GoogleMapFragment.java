package com.cafejeunesse.android.fragment.mapfragment;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cafejeunesse.android.database.ServiceDataSource;
import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Service;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by David Levayer on 21/03/15.
 */
public class GoogleMapFragment extends BasicFragment
        implements GoogleMap.OnInfoWindowClickListener {

    protected View mView;
    protected Context mContext;

    private static int MAPS_LOCATION_UPDATE = 20000;

    private MapFragment mMapFragment;
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mLocation;

    private ServiceDataSource mDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initFragment(inflater, container, R.layout.googlemapfragment_main);
        return inflater.inflate(R.layout.googlemapfragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Chargement d'un sous-fragment contenant la map
        FragmentManager fm = getChildFragmentManager();
        mMapFragment = (MapFragment) fm.findFragmentById(R.id.map_container);
        if (mMapFragment == null) {
            // On créé une instance de MapFragment (il ne faut pas passer par le constructeur !)
            mMapFragment = MapFragment.newInstance();
            // Chargement effectif du sous-fragment
            fm.beginTransaction().replace(R.id.map_container, mMapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap == null) {
            // Au besoin (pas déjà fait), on recharge la map
            mMap = mMapFragment.getMap();
            setUpMap();
        }
        if(mDataSource != null)
            mDataSource.open();
    }

    @Override
    public void onPause() {
        if(mDataSource != null)
            mDataSource.close();
        super.onPause();
    }

    private void setUpMap() {

        // Trigger les clics sur les descriptions des markers
        mMap.setOnInfoWindowClickListener(this);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getActivity().getLayoutInflater().inflate(
                        R.layout.googlemapfragment_info_marker,
                        null);

                TextView info_title = (TextView) v.findViewById(R.id.marker_title);
                info_title.setText(marker.getTitle());

                //TextView info = (TextView) v.findViewById(R.id.info_content);
                //info.setText(marker.getSnippet());
                return v;
            }
        });

        mMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = mLocationManager.getBestProvider(criteria, true);

        // Getting Current Location
        mLocation = mLocationManager.getLastKnownLocation(provider);

        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if(location!=null) {
                    mLocation = location;
                }
            }
            public void onProviderDisabled(String provider) { }
            public void onProviderEnabled(String provider) { }
            public void onStatusChanged(String provider, int status, Bundle extras) { }
        };

        if(mLocation !=null)
        {
            CameraUpdate center= CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLocation.getLatitude(), mLocation.getLongitude()),
                    15);

            mMap.moveCamera(center);
        }

        loadMarkers();

        mLocationManager.requestLocationUpdates(
                provider,
                MAPS_LOCATION_UPDATE,
                0,
                mLocationListener);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Bundle b = new Bundle();
        b.putString(GoogleMapDialogFragment.TITLE,marker.getTitle());

        FragmentManager fm = getFragmentManager();
        GoogleMapDialogFragment mDialogFragment = new GoogleMapDialogFragment();
        mDialogFragment.setArguments(b);
        mDialogFragment.show(fm, "googlemap_dialog_fragment");
    }

    private void loadMarkers(){

        if(mDataSource == null){
            mDataSource = new ServiceDataSource(getActivity());

            mDataSource.open();
            List<Service> values = mDataSource.getAllCustomMarkers();

            if(values.size()==0) {
                //loadSampleValues(mDataSource);
                //values = mDataSource.getAllCustomMarkers();
            }

            for(Service service: values){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(service.getLat(), service.getLon()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(service.getServiceName()));
            }
        }

    }
}
