package co.crossroadsapp.leagueoflegends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.crossroadsapp.leagueoflegends.data.ActivityData;
import co.crossroadsapp.leagueoflegends.data.EventData;
import co.crossroadsapp.leagueoflegends.data.PlayerData;
import co.crossroadsapp.leagueoflegends.data.ReviewCardData;
import co.crossroadsapp.leagueoflegends.data.UserData;
import co.crossroadsapp.leagueoflegends.utils.Constants;
import co.crossroadsapp.leagueoflegends.utils.Util;

/**
 * Created by sharmha on 9/9/16.
 */
public class EventCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ReviewCardData reviewCardData;
    private ControlManager mManager;
    private View view;
    protected ArrayList<EventData> elistLocal;
    protected ArrayList<ActivityData> adList;
    boolean publicEventCard;
    boolean showFullEvent;
    UserData user;
    Activity mContext;

    public EventCardAdapter(ArrayList<EventData> currentEventList, ArrayList<ActivityData> currentAdList, ReviewCardData reviewCard, Activity act, ControlManager manager, int feed) {
        elistLocal = new ArrayList<EventData>();
        adList = new ArrayList<ActivityData>();
        reviewCardData = new ReviewCardData();

        mManager = manager!=null?manager:null;
        if(mManager!=null && mManager.getUserData()!=null) {
            user = mManager.getUserData();
        }
        mContext = act!=null?act:null;
        if(feed==Constants.PUBLIC_EVENT_FEED) {
            publicEventCard = true;
            if(mManager!=null && mManager.getshowFullEvent()) {
                showFullEvent = true;
            }
        }

        if(currentEventList!=null) {
            elistLocal = currentEventList;
        }
        if(currentAdList !=null) {
            adList = currentAdList;
        }
        if(reviewCard!=null && reviewCard.getmCardText()!=null) {
            reviewCardData = reviewCard;
        }
        checkFullEventPreview();
    }

    private void checkFullEventPreview() {
        if(elistLocal!=null && !elistLocal.isEmpty()) {
            if(publicEventCard && !showFullEvent) {
                for (int i =0; i< elistLocal.size(); i++) {
                    if(elistLocal.get(i)!=null) {
                        if(elistLocal.get(i).getEventStatus()!=null && elistLocal.get(i).getEventStatus().equalsIgnoreCase("full")) {
                            elistLocal.remove(i);
                            i--;
                        }
                    }
                }
            }
        }
    }

    protected void addItem(ArrayList<EventData> a, ArrayList<ActivityData> ad, ReviewCardData reviewData) {
        if(a!=null) {
            this.elistLocal.addAll(a);
        }
        if(ad!=null) {
            this.adList.addAll(ad);
        }
        if(reviewData!=null) {
            this.reviewCardData = mManager.getReviewCard();
        }
        checkFullEventPreview();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout event_card_mainLayout;
        private CardView event_card;
        protected TextView eventSubType;
        protected TextView eventPlayerNames;
        protected TextView eventaLight;
        protected TextView eventPlayerNameCnt;
        protected ImageView eventIcon;
        protected ImageView playerProfileImage1;
        protected ImageView playerProfileImage2;
        protected ImageView playerProfileImage3;
        protected TextView playerCountImage3;
        protected ImageView joinBtn;
        protected ImageView unjoinBtn;
        protected TextView eventDate;
        protected TextView checkpointText;
        protected TextView tagText;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            event_card_mainLayout = (RelativeLayout) v.findViewById(R.id.fragment_event_mainlayout);
            event_card = (CardView) v.findViewById(R.id.event);
            eventSubType = (TextView) v.findViewById(R.id.activity_name);
            eventPlayerNames = (TextView) v.findViewById(R.id.activity_player_name);
            eventaLight = (TextView) v.findViewById(R.id.activity_aLight);
            eventIcon = (ImageView) v.findViewById(R.id.event_icon);
            playerProfileImage1 = (ImageView) v.findViewById(R.id.player_picture_1);
            playerProfileImage2 = (ImageView) v.findViewById(R.id.player_picture_2);
            playerProfileImage3 = (ImageView) v.findViewById(R.id.player_picture_3);
            playerCountImage3 = (TextView) v.findViewById(R.id.player_picture_text_3);
            joinBtn = (ImageView) v.findViewById(R.id.join_btn);
            unjoinBtn = (ImageView) v.findViewById(R.id.unjoin_btn);
            eventPlayerNameCnt = (TextView) v.findViewById(R.id.activity_player_name_lf);
            eventDate = (TextView) v.findViewById(R.id.event_time);
            checkpointText = (TextView) v.findViewById(R.id.checkoint_text);
            tagText = (TextView) v.findViewById(R.id.tag_text);
        }
    }

    //Viewholder for ad cards
    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        private RelativeLayout event_adcard_mainLayout;
        private ImageView adCardImg;
        private CardView event_adcard;
        private CardView addBtn;
        protected TextView eventadHeader;
        protected TextView eventAdSubheader;
        protected ImageView eventAdIcon;

        public MyViewHolder2(View v) {
            super(v);
            view = v;
            adCardImg = (ImageView) v.findViewById(R.id.adcard_img);
            event_adcard_mainLayout = (RelativeLayout) v.findViewById(R.id.fragment_adevent_mainlayout);
            event_adcard = (CardView) v.findViewById(R.id.ad_event);
            eventadHeader = (TextView) v.findViewById(R.id.ad_header);
            eventAdSubheader = (TextView) v.findViewById(R.id.ad_subheader);
            eventAdIcon = (ImageView) v.findViewById(R.id.adevent_icon);
            addBtn = (CardView) v.findViewById(R.id.adcard_addbtn);
        }
    }

    //Viewholder for ad cards
    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        private ImageView reviewCardImg;
        protected TextView reviewText;
        protected TextView reviewButtonText;
        protected ImageView reviewCancel;
        protected RelativeLayout reviewBtnLayout;

        public MyViewHolder3(View v) {
            super(v);
            view = v;
            reviewCardImg = (ImageView) v.findViewById(R.id.reviewcard_img);
            reviewBtnLayout = (RelativeLayout) v.findViewById(R.id.review_btn);
            reviewText = (TextView) v.findViewById(R.id.review_text);
            reviewButtonText = (TextView) v.findViewById(R.id.review_btn_text);
            reviewCancel = (ImageView) v.findViewById(R.id.noreview_btn);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(position==0 && reviewCardData!=null && reviewCardData.getmName()!=null) {
            return 4;
        } else if (elistLocal.size() == 0) {
        //if (position == 0 && elistLocal.size() == 0) {
            return 2;
        } else {
            if(reviewCardData!=null && reviewCardData.getmName()!=null) {
                if(position <= elistLocal.size()) {
                    return 0;
                }
            } else if (position < elistLocal.size()){
                return 0;
            }
            return 2;
        }
//        if(position==0 && reviewCardData!=null && reviewCardData.getmName()!=null) {
//            return 4;
//        } else if (elistLocal.size() > 0) {
//            if (reviewCardData.getmName()!=null && position <= elistLocal.size()) {
//                return 0;
//            } else if(position<elistLocal.size()) {
//                return 2;
//            }
//        } else if (position <= elistLocal.size()) {
//            if(reviewCardData.getmName()!=null) {
//                return 0;
//            }
//            return 2;
//        } else {
//            return 2;
//        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh = null;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_event, null);
                return new MyViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_adevent, null);
                return new MyViewHolder2(view);
            case 4:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_reviewcard, null);
                return new MyViewHolder3(view);
        }
        //return new MyViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                final MyViewHolder holder = (MyViewHolder) viewHolder;
                if (this.elistLocal.size() > 0) {
                    boolean CreatorIn = false;
                    boolean CreatorIsPlayer = false;
                    String allNames = "";
                    String allNamesRem = "";
                    String checkpoint = "";
                    String tag = "";
                    String clanT = "";
                    final EventData currEvent;
                    int eventPosition=0;
                    if(reviewCardData!=null && reviewCardData.getmCardText()!=null) {
                        eventPosition = position-1;
                    } else {
                        eventPosition = position;
                    }
                    currEvent = elistLocal.get(eventPosition);
                    final String eId = this.elistLocal.get(eventPosition).getEventId();
                    String activityName = this.elistLocal.get(eventPosition).getActivityData().getActivityType();
                    String s = this.elistLocal.get(eventPosition).getActivityData().getActivitySubtype();
                    int l = this.elistLocal.get(eventPosition).getActivityData().getActivityLight();
                    String url = this.elistLocal.get(eventPosition).getActivityData().getActivityIconUrl();
                    int reqPlayer = this.elistLocal.get(eventPosition).getActivityData().getMaxPlayer() - this.elistLocal.get(eventPosition).getPlayerData().size();
                    // get players
                    int i = this.elistLocal.get(eventPosition).getPlayerData().size();
                    String level = this.elistLocal.get(eventPosition).getActivityData().getActivityLevel();
                    String creatorId = this.elistLocal.get(eventPosition).getCreatorData().getPlayerId();
                    final String status = this.elistLocal.get(eventPosition).getEventStatus();
                    if(!publicEventCard) {
                        tag = this.elistLocal.get(eventPosition).getActivityData().getTag();
                    }
                    checkpoint = this.elistLocal.get(eventPosition).getActivityData().getActivityCheckpoint();
                    //holder.checkpointText.setVisibility(View.GONE);
                    holder.eventDate.setVisibility(View.GONE);
                    holder.tagText.setVisibility(View.GONE);
                    if(tag!=null && !tag.isEmpty()) {
                        setCardViewLayoutParams(holder.event_card_mainLayout, 172);
                        holder.tagText.setVisibility(View.VISIBLE);
                        holder.tagText.setText(tag);
                        Util.roundCorner(holder.tagText, mContext);
                    }else {
                        setCardViewLayoutParams(holder.event_card_mainLayout, 137);
                    }
                    if (creatorId != null) {
                        if (user != null && user.getUserId() != null) {
                            if (creatorId.equalsIgnoreCase(user.getUserId())) {
                                CreatorIn = true;
                                CreatorIsPlayer = true;
                            }
                        }
                    }
                    if (elistLocal.get(eventPosition).getLaunchEventStatus().equalsIgnoreCase(Constants.LAUNCH_STATUS_UPCOMING) || (checkpoint != null)) {
                        if (elistLocal.get(eventPosition).getLaunchEventStatus().equalsIgnoreCase(Constants.LAUNCH_STATUS_UPCOMING)) {
                            String date = Util.convertUTCtoReadable(elistLocal.get(eventPosition).getLaunchDate());
                            if (date != null) {
                                holder.eventDate.setVisibility(View.VISIBLE);
                                holder.eventDate.setText(date);
                            }
                            if (checkpoint != null && checkpoint.length() > 0 && (!checkpoint.equalsIgnoreCase("null"))) {
//                                holder.checkpointText.setVisibility(View.VISIBLE);
//                                holder.checkpointText.setText(checkpoint);
                                if(tag!=null && !tag.isEmpty()) {
                                    setCardViewLayoutParams(holder.event_card_mainLayout, 212);
                                    holder.tagText.setVisibility(View.VISIBLE);
                                    holder.tagText.setText(tag);
                                    Util.roundCorner(holder.tagText, mContext);
                                }else {
                                    setCardViewLayoutParams(holder.event_card_mainLayout, 177);
                                }
                            } else {
//                                holder.checkpointText.setVisibility(View.GONE);
                                if(tag!=null && !tag.isEmpty()) {
                                    setCardViewLayoutParams(holder.event_card_mainLayout, 190);
                                    holder.tagText.setVisibility(View.VISIBLE);
                                    holder.tagText.setText(tag);
                                    Util.roundCorner(holder.tagText, mContext);
                                }else {
                                    setCardViewLayoutParams(holder.event_card_mainLayout, 155);
                                }
                            }
                        } else if (checkpoint != null && checkpoint.length() > 0 && (!checkpoint.equalsIgnoreCase("null"))) {
//                            holder.checkpointText.setVisibility(View.VISIBLE);
//                            holder.checkpointText.setText(checkpoint);
                            if(tag!=null && !tag.isEmpty()) {
                                setCardViewLayoutParams(holder.event_card_mainLayout, 190);
                                holder.tagText.setVisibility(View.VISIBLE);
                                holder.tagText.setText(tag);
                                Util.roundCorner(holder.tagText, mContext);
                            }else {
                                setCardViewLayoutParams(holder.event_card_mainLayout, 155);
                            }
                        }
                    }

                    for (int y = 0; y < i; y++) {
                        boolean thisIsUnverifiedUser = false;
                        String n = this.elistLocal.get(eventPosition).getPlayerData().get(y).getPsnId();
                        String verifyStatus = this.elistLocal.get(eventPosition).getPlayerData().get(y).getPsnVerify();
                        String profileUrl = this.elistLocal.get(eventPosition).getPlayerData().get(y).getPlayerImageUrl();
                        String pId = this.elistLocal.get(eventPosition).getPlayerData().get(y).getPlayerId();
                        if (user != null && user.getUserId() != null) {
                            if (user.getUserId().equalsIgnoreCase(pId)) {
                                CreatorIn = true;
                                if(user.getPsnVerify()!=null && !user.getPsnVerify().equalsIgnoreCase(Constants.PSN_VERIFIED)) {
                                    thisIsUnverifiedUser = true;
                                }
                            }
                        }

                        if(y < 4) {
                            if(verifyStatus!=null && !verifyStatus.equalsIgnoreCase(Constants.PSN_VERIFIED)) {
                                uploadPlayerImg(holder, null, y, i);
                            } else {
                                uploadPlayerImg(holder, profileUrl, y, i);
                            }
                        }
                    }

                    allNames = this.elistLocal.get(eventPosition).getCreatorData().getPsnId();

//                    if(CreatorIsPlayer) {
//                        if(user!=null && user.getPsnVerify()!=null && user.getPsnVerify().equalsIgnoreCase(Constants.PSN_VERIFIED)) {
//                            clanT = this.elistLocal.get(position).getCreatorData().getClanTag()!=null?this.elistLocal.get(position).getCreatorData().getClanTag():"";
//                        }
//                    } else {
//                        clanT = this.elistLocal.get(position).getCreatorData().getClanTag()!=null?this.elistLocal.get(position).getCreatorData().getClanTag():"";
//                    }

                    if (this.elistLocal.get(eventPosition).getCreatorData().getClanTag() != null) {
                        clanT = this.elistLocal.get(eventPosition).getCreatorData().getClanTag();
                        if (clanT!=null && !clanT.isEmpty()) {
                            allNames = allNames + " [" + clanT + "]";
                        }
                    }
                    if (!status.equalsIgnoreCase("full")) {
                        allNamesRem = " " + "LF" + reqPlayer + "M";
                    }

                    if(!publicEventCard) {
                        if (status != null && !status.equalsIgnoreCase("")) {
                            if (!status.equalsIgnoreCase(Constants.STATUS_FULL) && !CreatorIn) {
                                holder.joinBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RequestParams rp = new RequestParams();
                                        rp.put("eId", eId);
                                        rp.put("player", user.getUserId());
                                        if (mContext != null) {
                                            ((ListActivityFragment) mContext).hideProgress();
                                            ((ListActivityFragment) mContext).showProgress();
                                            mManager.postJoinEvent(rp);
                                            holder.joinBtn.setClickable(false);
                                        }
                                    }
                                });
                            } else if (CreatorIn) {
                                holder.unjoinBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RequestParams rp = new RequestParams();
                                        rp.put("eId", eId);
                                        rp.put("player", user.getUserId());
                                        if (mContext != null) {
                                            ((ListActivityFragment) mContext).hideProgress();
                                            ((ListActivityFragment) mContext).showProgress();
                                            mManager.postUnJoinEvent(rp);
                                            holder.unjoinBtn.setClickable(false);
                                        }
                                    }
                                });
                            }
                        }
                    }
                    final boolean showJoin;
                    if (!status.equalsIgnoreCase(Constants.STATUS_FULL) && !CreatorIn) {
                        showJoin = true;
                    } else {
                        showJoin = false;
                    }

                    if (s != "" || s != null) {
                        holder.checkpointText.setText(s);
                    }

                    if(activityName!=null && !activityName.isEmpty()) {
                        holder.eventSubType.setText(activityName!=null?activityName.replaceAll(".*,", "").trim():activityName);
                    }

                    if(!publicEventCard) {
                        holder.event_card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (currEvent != null) {
                                    if(currEvent.getEventId()!=null) {
                                        ((ListActivityFragment) mContext).hideProgressBar();
                                        ((ListActivityFragment) mContext).showProgressBar();
                                        ((ListActivityFragment) mContext).getEventById(currEvent.getEventId());
                                    }
//                                    CurrentEventDataHolder ins = CurrentEventDataHolder.getInstance();
//                                    ins.setData(currEvent);
//                                    if (showJoin) {
//                                        ins.setJoinVisible(true);
//                                    } else {
//                                        ins.setJoinVisible(false);
//                                    }
                                    //setCurrEventData(currEvent);
                                }
//                                if (mContext != null) {
//                                    //start new activity for event
//                                    Intent regIntent = new Intent(mContext,
//                                            EventDetailActivity.class);
//                                    if (regIntent != null) {
//                                        //regIntent.putExtra("userdata", user);
//                                        mContext.startActivity(regIntent);
//                                        //mContext.finish();
//                                    }
//                                }
                            }
                        });
                    }

//                        if (l > 0) {
//                            // unicode to show star
//                            String st = "\u2726";
//                            holder.eventaLight.setText(st + String.valueOf(l));
//                        } else if (level > 0) {
//                            holder.eventaLight.setText("lvl " + String.valueOf(level));
//                        }
                    if(publicEventCard) {
                        if(status.equalsIgnoreCase("full")) {
                            holder.unjoinBtn.setVisibility(View.GONE);
                            holder.joinBtn.setImageResource(R.drawable.btn_f_u_l_l);
                        } else {
                            holder.unjoinBtn.setVisibility(View.GONE);
                            holder.joinBtn.setImageResource(R.drawable.btn_j_o_i_n_public);
                        }

                        setCardViewLayoutParams(holder.event_card_mainLayout, 165);
                    } else {
                        String feed = this.elistLocal.get(eventPosition).getActivityData().getaFeedMode()!=null?this.elistLocal.get(eventPosition).getActivityData().getaFeedMode():"";
                        holder.eventaLight.setText(feed.toUpperCase());
                        updateJoinButton(holder, status, CreatorIn, CreatorIsPlayer, ifUserIsInvited(currEvent.getPlayerData()));
                    }

                    holder.eventPlayerNames.setText(allNames);
                    holder.eventPlayerNameCnt.setText(allNamesRem);
                    holder.eventaLight.invalidate();
                    holder.event_card_mainLayout.invalidate();
                    holder.checkpointText.invalidate();
                    holder.eventDate.invalidate();
                    Util.picassoLoadIcon(mContext, holder.eventIcon, url, R.dimen.activity_icon_hgt, R.dimen.activity_icon_width, R.drawable.icon_ghost_default);
                }
                break;
            case 2:
                MyViewHolder2 adHolder = (MyViewHolder2) viewHolder;
                int adcardPosition=0;
                if(reviewCardData!=null && reviewCardData.getmCardText()!=null) {
                    adcardPosition = position-elistLocal.size()-1;
                } else {
                    adcardPosition = position-elistLocal.size();
                }

                final String adId = adList.get(adcardPosition).getId();
                final String adActivityType = adList.get(adcardPosition).getActivityType();
                adHolder.eventadHeader.setText(adList.get(adcardPosition).getAdCardData().getAdCardHeader());
                adHolder.eventAdSubheader.setText(adList.get(adcardPosition).getAdCardData().getAdCardSubHeader());
                String cardBackgroundImageUrl = adList.get(adcardPosition).getAdCardData().getAdCardBaseUrl() + adList.get(adcardPosition).getAdCardData().getAdCardImagePath();
                String iconImageUrl = adList.get(adcardPosition).getActivityIconUrl();
                Util.picassoLoadIcon(mContext, adHolder.eventAdIcon, iconImageUrl, R.dimen.activity_icon_hgt, R.dimen.activity_icon_width, R.drawable.icon_ghost_default);
                //Util.picassoLoadIcon(mContext, adHolder.adCardImg, cardBackgroundImageUrl, R.dimen.ad_hgt, R.dimen.ad_width, R.drawable.img_adcard_raid_golgoroth);
                if(mContext!=null) {
                    Picasso.with(mContext)
                            .load(cardBackgroundImageUrl)
                            .placeholder(null)
                            .fit().centerCrop()
                            .into(adHolder.adCardImg);
                }

                adHolder.event_adcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mContext != null && mContext instanceof ListActivityFragment) {
                            ((ListActivityFragment) mContext).showProgressBar();
                            ((ListActivityFragment) mContext).setAdCardPosition(adId);
                            RequestParams rp = new RequestParams();
                            rp.add("aType", adActivityType);
                            rp.add("includeTags", "true");
                            mManager.postGetActivityList(rp);

                            //tracking adcard click
                            Map<String, String> json = new HashMap<String, String>();
                            if (adId != null) {
                                json.put("activityId", adId.toString());
                                Util.postTracking(json, mContext, mManager, Constants.APP_ADCARD);
                            }
                            //mManager.postCreateEvent(adList.get(position-elistLocal.size()).getId(), user.getUserId(), adList.get(position-elistLocal.size()).getMinPlayer(), adList.get(position-elistLocal.size()).getMaxPlayer(), null, mContext);
                        }
                    }
                });
                break;
            case 4:
                MyViewHolder3 reviewHolder = (MyViewHolder3) viewHolder;
                String cardBackground = reviewCardData.getmImageUrl();
                String btnText = reviewCardData.getmButtonText();
                String cardText = reviewCardData.getmCardText();
                if(mContext!=null) {
                    Picasso.with(mContext)
                            .load(cardBackground)
                            .placeholder(null)
                            .fit().centerCrop()
                            .into(reviewHolder.reviewCardImg);
                }
                if(btnText!=null) {
                    reviewHolder.reviewButtonText.setText(btnText);
                }
                if(cardText!=null) {
                    reviewHolder.reviewText.setText(cardText);
                }

                reviewHolder.reviewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update backend
                        RequestParams params = new RequestParams();
                        params.put("reviewPromptCardStatus", "REFUSED");
                        mManager.postReviewUpdate(params);

                        Intent regIntent = new Intent(mContext,
                                CrashReport.class);
                        //regIntent.putExtra("userdata", user);
                        mContext.startActivity(regIntent);

                        reviewCardData = null;
                    }
                });

                reviewHolder.reviewBtnLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update backend
                        RequestParams params = new RequestParams();
                        params.put("reviewPromptCardStatus", "COMPLETED");
                        mManager.postReviewUpdate(params);

                        Util.openPlayStore(mManager.getCurrentActivity());

                        reviewCardData = null;
                    }
                });
                break;
        }
    }

    private boolean ifUserIsInvited(ArrayList<PlayerData> playerData) {
        if(user!=null && user.getUserId()!=null) {
            if(playerData!=null) {
                for(int i=0;i<playerData.size();i++) {
                    if(playerData.get(i)!=null && playerData.get(i).getUserId().equalsIgnoreCase(user.getUserId())) {
                        if(playerData.get(i).getIsInvited()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private void setCardViewLayoutParams(RelativeLayout event_card_mainLayout, int i) {
        int pix = Util.dpToPx(i, mContext);
        if(pix>0) {
            event_card_mainLayout.getLayoutParams().height = pix;
            event_card_mainLayout.requestLayout();
        }
    }

    private void updateJoinButton(MyViewHolder holder, String status, boolean creatorIn, boolean creatorIsPlayer, boolean playerIsInvited) {
        switch (status) {
            case "new":
                if (creatorIsPlayer) {
                    holder.unjoinBtn.setVisibility(View.VISIBLE);
                    holder.joinBtn.setImageResource(R.drawable.btn_o_w_n_e_r);
                } else if (creatorIn) {
                    if(playerIsInvited) {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_i_n_v_i_t_e_d);
                    }else {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_g_o_i_n_g);
                    }
                } else {
                    holder.unjoinBtn.setVisibility(View.GONE);
                    holder.joinBtn.setImageResource(R.drawable.btn_j_o_i_n);
                }
                break;
            case "can_join":
                if (creatorIsPlayer) {
                    holder.unjoinBtn.setVisibility(View.VISIBLE);
                    holder.joinBtn.setImageResource(R.drawable.btn_o_w_n_e_r);
                } else if (creatorIn) {
                    if(playerIsInvited) {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_i_n_v_i_t_e_d);
                    }else {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_g_o_i_n_g);
                    }
                } else {
                    holder.unjoinBtn.setVisibility(View.GONE);
                    holder.joinBtn.setImageResource(R.drawable.btn_j_o_i_n);
                }
                break;
            case "full":
                if (creatorIn) {
                    if(playerIsInvited) {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_i_n_v_i_t_e_d);
                    }else {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_r_e_a_d_y);
                    }
                } else {
                    holder.unjoinBtn.setVisibility(View.GONE);
                    holder.joinBtn.setImageResource(R.drawable.btn_f_u_l_l);
                }
                break;
            case "open":
                if (creatorIsPlayer) {
                    holder.unjoinBtn.setVisibility(View.VISIBLE);
                    holder.joinBtn.setImageResource(R.drawable.btn_o_w_n_e_r);
                } else if (creatorIn) {
                    if(playerIsInvited) {
                        holder.unjoinBtn.setVisibility(View.VISIBLE);
                        holder.joinBtn.setImageResource(R.drawable.btn_i_n_v_i_t_e_d);
                    }else {
                        if(playerIsInvited) {
                            holder.unjoinBtn.setVisibility(View.VISIBLE);
                            holder.joinBtn.setImageResource(R.drawable.btn_i_n_v_i_t_e_d);
                        }else {
                            holder.unjoinBtn.setVisibility(View.VISIBLE);
                            holder.joinBtn.setImageResource(R.drawable.btn_g_o_i_n_g);
                        }
                    }
                } else {
                    holder.unjoinBtn.setVisibility(View.GONE);
                    holder.joinBtn.setImageResource(R.drawable.btn_j_o_i_n);
                }
                break;
        }
    }

    private void uploadPlayerImg(MyViewHolder holder, String profileUrl, int player, int playersCount) {
        switch (player) {
            case 0:
                holder.playerProfileImage3.setVisibility(View.GONE);
                holder.playerProfileImage2.setVisibility(View.GONE);
                holder.playerCountImage3.setVisibility(View.GONE);
                Util.picassoLoadIcon(mContext, holder.playerProfileImage1, profileUrl, R.dimen.player_icon_hgt, R.dimen.player_icon_width, R.drawable.profile_image);
                break;
            case 1:
                holder.playerProfileImage2.setVisibility(View.VISIBLE);
                holder.playerCountImage3.setVisibility(View.GONE);
                Util.picassoLoadIcon(mContext, holder.playerProfileImage2, profileUrl, R.dimen.player_icon_hgt, R.dimen.player_icon_width, R.drawable.profile_image);
                break;
            default:
                holder.playerProfileImage3.setVisibility(View.VISIBLE);
                if (player == 2 && playersCount < 4) {
                    holder.playerCountImage3.setVisibility(View.GONE);
                    Util.picassoLoadIcon(mContext, holder.playerProfileImage3, profileUrl, R.dimen.player_icon_hgt, R.dimen.player_icon_width, R.drawable.profile_image);
                } else {
                    holder.playerProfileImage3.setImageResource(R.drawable.img_avatar_empty);
                    holder.playerCountImage3.setVisibility(View.VISIBLE);
                    int p = playersCount - 2;
                    holder.playerCountImage3.setText("+" + p);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(elistLocal!=null && adList!=null) {
            if(reviewCardData!=null && reviewCardData.getmId()!=null) {
                return this.elistLocal.size()+this.adList.size()+1;
            }
            return this.elistLocal.size()+this.adList.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
