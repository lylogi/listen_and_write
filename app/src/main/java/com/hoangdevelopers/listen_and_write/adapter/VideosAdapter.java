package com.hoangdevelopers.listen_and_write.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdevelopers.listen_and_write.R;
import com.hoangdevelopers.listen_and_write.YouTubeFullModeLesson;
import com.hoangdevelopers.listen_and_write.models.VideoData;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideosAdapter extends
        RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private List<VideoData> videoDatas;
    private Context context;

    public VideosAdapter(List<VideoData> _videoDatas) {
        videoDatas = _videoDatas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View videoView = inflater.inflate(R.layout.video_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(videoView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        VideoData videoData = videoDatas.get(position);

        // Set item views based on your views and data model
        TextView titleView = holder.title;
        titleView.setText(videoData.getTitle());

        holder.videoDuration.setText(videoData.getDuration());
        holder.videoLevel.setText("Level: " + videoData.getLevel());
        holder.channelName.setText(videoData.getChannelMeta().getName());
        Picasso.with(context).load(videoData.getThumbnail()).into(holder.thumbnail);
        Picasso.with(context).load(videoData.getChannelMeta().getAvatar()).into(holder.avatarChannel);

        holder.setItemClickListener(new ViewHolder.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, YouTubeFullModeLesson.class);
                intent.putExtra("video_url", videoData.getVideoUrl());
                intent.putExtra("video_title", videoData.getTitle());
                intent.putExtra("sub_url", videoData.getSubUrl());
                intent.putExtra("sub_type", videoData.getSubType());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public interface ItemClickListener {

            void onClick(View view, int position, boolean isLongClick);
        }

        private ItemClickListener itemClickListener;
        public ImageView thumbnail;
        public TextView title;
        public TextView videoDuration;
        public TextView videoLevel;
        public CircleImageView avatarChannel;
        public TextView channelName;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            title = (TextView) itemView.findViewById(R.id.video_title);
            channelName = (TextView) itemView.findViewById(R.id.video_channel);
            videoDuration = (TextView) itemView.findViewById(R.id.video_duration);
            videoLevel = (TextView) itemView.findViewById(R.id.video_level);
            avatarChannel = (CircleImageView) itemView.findViewById(R.id.video_avatar);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
