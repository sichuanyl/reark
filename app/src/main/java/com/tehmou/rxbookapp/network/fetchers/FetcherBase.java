package com.tehmou.rxbookapp.network.fetchers;

import android.util.Log;

import com.tehmou.rxbookapp.data.stores.NetworkRequestStatusStore;
import com.tehmou.rxbookapp.network.NetworkApi;
import com.tehmou.rxbookapp.pojo.NetworkRequestStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ttuo on 16/04/15.
 */
abstract public class FetcherBase implements Fetcher {
    private static final String TAG = FetcherBase.class.getSimpleName();

    protected final String owner;
    protected final NetworkApi networkApi;
    private final Action1<NetworkRequestStatus> updateNetworkRequestStatus;
    protected final Map<Integer, Subscription> requestMap = new ConcurrentHashMap<>();

    public FetcherBase(String owner,
                       NetworkApi networkApi,
                       Action1<NetworkRequestStatus> updateNetworkRequestStatus) {
        this.owner = owner;
        this.networkApi = networkApi;
        this.updateNetworkRequestStatus = updateNetworkRequestStatus;
    }

    protected void startRequest(String uri) {
        Log.v(TAG, "startRequest(" + uri + ", " + owner + ")");
        updateNetworkRequestStatus.call(new NetworkRequestStatus(
                        uri, owner, NetworkRequestStatus.NETWORK_STATUS_ONGOING));
    }

    protected void errorRequest(String uri, Throwable error) {
        Log.v(TAG, "errorRequest(" + uri + ", " + owner + ", " + error + ")");
        updateNetworkRequestStatus.call(new NetworkRequestStatus(
                        uri, owner, NetworkRequestStatus.NETWORK_STATUS_ERROR));
    }

    protected void completeRequest(String uri) {
        Log.v(TAG, "completeRequest(" + uri + ", " + owner + ")");
        updateNetworkRequestStatus.call(new NetworkRequestStatus(
                        uri, owner, NetworkRequestStatus.NETWORK_STATUS_COMPLETED));
    }
}
