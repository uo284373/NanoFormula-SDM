package com.example.nanoformula.API;

import android.os.AsyncTask;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;

import java.util.List;

public class YouTubeSearchTask extends AsyncTask<String, String, String> {
    public interface OnSearchCompleteListener {
        void onSearchComplete(String url);
    }

    private OnSearchCompleteListener listener;

    public YouTubeSearchTask(OnSearchCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String title = params[0];
        return searchYouTubeByTitle(title);
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onSearchComplete(result);
        }
    }

    private String searchYouTubeByTitle(String title) {
        try {
            YouTube youtube = new YouTube.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    null
            ).setApplicationName("NanoFormula").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey("AIzaSyBwkydR0gjtLTT9Du9QNm-O8CBQ57M1f-A");
            search.setQ(title);
            search.setChannelId("UC89aCe0fFyScFU-NegT2CFQ");
            search.setType("video");
            search.setMaxResults(1L);

            SearchListResponse response = search.execute();
            List<SearchResult> items = response.getItems();

            if (items != null && !items.isEmpty()) {
                SearchResult video = items.get(0);
                SearchResultSnippet snippet = video.getSnippet();
                String videoId = video.getId().getVideoId();
                String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

                return videoUrl;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
