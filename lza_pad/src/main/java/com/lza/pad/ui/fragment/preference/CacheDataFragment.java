package com.lza.pad.ui.fragment.preference;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.task.EbookTask;
import com.lza.pad.core.request.task.RequestTask;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.lib.support.utils.UniversalUtility;
import com.lza.pad.service.ICacheData;
import com.lza.pad.ui.fragment.AbstractListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class CacheDataFragment extends AbstractListFragment implements Consts {

    private NavigationInfo mNav;
    private Lock mLock = new ReentrantLock();
    private List<RequestTask> mTaskArray = new ArrayList<RequestTask>();
    private List<Map<String, String>> mTaskMsg = new ArrayList<Map<String, String>>();

    private int mTaskFinishedCount = 0;
    private int mTaskCount = 0;

    private List<EbookTask> mEbookTaskArray = new ArrayList<EbookTask>();
    private int mEbookTaskFinishedCount = 0;
    private int mEbookTaskCount = 0;

    private int mOneEbookTaskCount = 0;
    private int mEbookCurrentIndex = 0;

    private SimpleAdapter mAdapter;

    private Button mBtnStartCache, mBtnBack, mBtnEditPage;
    private TextView mTxtPrompt, mTxtPagePropmt;

    private ArrayList<Ebook> mEbooks;

    private int mInitPage = 1;
    private int mFinalPage = -1;
    private int mCurrentPage = 0;
    private int mMaxPage = -1;

    //测试IPC
    private Button mBtnTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            mNav = arg.getParcelable(KEY_NAVIGATION_INFO);
        }
    }

    private boolean mIsCache = false;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cache_data, container, false);
        mBtnStartCache = (Button) view.findViewById(R.id.cache_data_start);
        mBtnBack = (Button) view.findViewById(R.id.cache_data_back);
        mBtnEditPage = (Button) view.findViewById(R.id.cache_data_edit_page);
        mTxtPrompt = (TextView) view.findViewById(R.id.cache_data_prompt);
        mTxtPagePropmt = (TextView) view.findViewById(R.id.cache_data_page_propmt);

        mBtnStartCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsCache) {
                    mHandler.sendEmptyMessage(TASK_INIT);
                    v.setEnabled(false);
                    mBtnEditPage.setEnabled(false);
                }
            }
        });
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.pref_container, new ModulePreferenceFragment())
                        .commit();
            }
        });
        mBtnEditPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editPageView = inflater.inflate(R.layout.pref_cache_set_pages, null);
                final EditText edtInitPage = (EditText) editPageView.findViewById(R.id.cache_data_init_page);
                final EditText edtFinalPage = (EditText) editPageView.findViewById(R.id.cache_data_final_page);
                edtInitPage.setText(String.valueOf(mInitPage));
                edtFinalPage.setText(String.valueOf(mFinalPage));
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.cache_edit_page)
                        .setView(editPageView)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    int initPage = Integer.valueOf(edtInitPage.getText().toString());
                                    int finalPage = Integer.valueOf(edtFinalPage.getText().toString());
                                    if (initPage > 0) {
                                        StringBuilder propmt = new StringBuilder();
                                        mInitPage = initPage;
                                        propmt.append("第").append(mInitPage).append("页 - ");
                                        if (finalPage == -1) {
                                            mFinalPage = finalPage;
                                            propmt.append("全部");
                                            mTxtPagePropmt.setText(propmt);
                                            ToastUtilsSimplify.show("更新成功！");
                                        } else if (finalPage > 0 && finalPage >= initPage) {
                                            mFinalPage = finalPage;
                                            propmt.append("第").append(mFinalPage).append("页");
                                            mTxtPagePropmt.setText(propmt);
                                            ToastUtilsSimplify.show("更新成功！");
                                        } else {
                                            ToastUtilsSimplify.show("更新失败！结尾页必须大于起始页！");
                                        }
                                    } else {
                                        ToastUtilsSimplify.show("更新失败！起始页必须大于0！");
                                    }
                                } catch (Exception e) {
                                    ToastUtilsSimplify.show("更新失败！");
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        mAdapter = new SimpleAdapter(
                getActivity(),
                mTaskMsg,
                R.layout.cache_data_adapter_item,
                new String[]{"count", "status", "ebook", "content"},
                new int[]{R.id.cache_data_item_title,
                        R.id.cache_data_item_status_info,
                        R.id.cache_data_item_img_info,
                        R.id.cache_data_item_content_info}
        );
        setListAdapter(mAdapter);

        mBtnTest = (Button) view.findViewById(R.id.cache_data_test);
        mBtnTest.setVisibility(View.GONE);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent("com.lza.pad.cache.DATA");
                getActivity().bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
            }
        });

        return view;
    }

    ICacheData mCache = null;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCache = ICacheData.Stub.asInterface(service);
            try {
                mCache.requestEbook(mNavInfo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mConnection = null;
            mCache = null;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        dismissProgressDialog();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLock.lock();
        if (mTaskArray != null && mTaskArray.size() > 0) {
            mTaskArray.clear();
            mHandler.sendEmptyMessage(TASK_BEGIN);
            VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(REQUEST_TAG);
        }
        mAdapter = null;
        mLock.unlock();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCache != null && mConnection != null) {
            if (getActivity() != null) {
                getActivity().unbindService(mConnection);
            }
        }
    }

    private void notifyListView() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            if (getListView() != null) {
                getListView().setSelection(mAdapter.getCount());
            }
        }
    }

    private void createNewEbookRequest() {
        mTaskCount++;
        RequestTask task = new RequestTask(mNav, mHandler);
        mTaskArray.add(task);
        if (mNav.getApiPagePar() <= mFinalPage || mFinalPage == -1) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "等待下载");
            map.put("count", mNav.getName() + " 第" + mNav.getApiPagePar() + "页");
            mTaskMsg.add(map);
        }
        /*map.put("count", "任务"  + mTaskCount +
                "：[control=" + mNav.getApiControlPar() +
                ",action=" + mNav.getApiActionPar() +
                ",page=" + mNav.getApiPagePar()+"]");*/
        mHandler.sendEmptyMessage(TASK_BEGIN);

    }

    private void updateEbookInitProgress() {
        mEbookCurrentIndex++;
        if (mOneEbookTaskCount > 0) {
            //更新进度条的显示
            float initProgress = (float) mEbookCurrentIndex / mOneEbookTaskCount;
            int finalProgress = (int) ((UniversalUtility.getFloatByHalfUp(initProgress)) * 100);
            /*AppLogger.e("mEbookCurrentIndex-->" + mEbookCurrentIndex +",mOneEbookTaskCount-->" + mOneEbookTaskCount + ",initProgress-->" + initProgress + ",finalProgress-->" + finalProgress);*/
            mTaskMsg.get(mTaskFinishedCount - 1).
                    put("status", "初始化 " + finalProgress + "%");
            notifyListView();
        }
    }

    private void updateEbookFinishProgress() {
        if (mOneEbookTaskCount > 0) {
            float fProgress = 0;
            int iProgress;
            //更新进度条的显示
            float finishCount = (float) (mEbookTaskFinishedCount) % mNav.getApiPageSizePar();
            if (mEbookTaskFinishedCount > 0 && finishCount == 0) {
                iProgress = 100;
            }else {
                fProgress = finishCount / mOneEbookTaskCount;
                iProgress = (int) ((UniversalUtility.getFloatByHalfUp(fProgress)) * 100);
            }
            /*AppLogger.e("finishCount-->" + finishCount + ",mOneEbookTaskCount-->" + mOneEbookTaskCount + ",fProgress-->" + fProgress + ",iProgress-->" + iProgress);*/
            mTaskMsg.get(mTaskFinishedCount - 1).
                    put("status", "已完成 " + iProgress + "%");
            notifyListView();
        }
    }

    private String getBookName() {
        Ebook ebook = mEbooks.get(mEbookTaskFinishedCount % mNav.getApiPageSizePar());
        if (ebook != null) {
            String bookName = ebook.getName();
            if (!TextUtils.isEmpty(bookName)) {
                return bookName;
            }else {
                bookName = ebook.getTitle_c();
                if (!TextUtils.isEmpty(bookName)) {
                    return bookName;
                }else {
                    bookName = ebook.getTitle();
                    if (!TextUtils.isEmpty(bookName)) {
                        return bookName;
                    }
                }
            }
        }
        return "未知图书";
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                /**
                 * 处理电子书的获取服务
                 */
                case TASK_INIT:
                    ToastUtilsSimplify.show("开始缓存...");
                    mLock.lock();
                    //指定起始页
                    mNav.setApiPagePar(mInitPage);
                    //创建请求
                    createNewEbookRequest();
                    mLock.unlock();
                    notifyListView();
                    break;
                case TASK_BEGIN:
                    mLock.lock();
                    int currentPage = mNav.getApiPagePar();
                    if (mFinalPage == -1 && mMaxPage != -1 && currentPage > mMaxPage) {
                        ToastUtilsSimplify.show("任务执行完毕！");
                        mIsCache = false;
                        mBtnStartCache.setEnabled(true);
                        mBtnEditPage.setEnabled(true);
                    } else if (mFinalPage != -1 && currentPage > mFinalPage) {
                        ToastUtilsSimplify.show("任务执行完毕！");
                        mIsCache = false;
                        mBtnStartCache.setEnabled(true);
                        mBtnEditPage.setEnabled(true);
                    } else if (mTaskArray.size() <= mTaskCount &&
                        mTaskArray.size() > mTaskFinishedCount) {
                        mTaskArray.get(mTaskFinishedCount).execute();
                        mTaskMsg.get(mTaskFinishedCount).put("status", "下载中...");
                    } else {
                        ToastUtilsSimplify.show("任务执行完毕！");
                        mIsCache = false;
                        mBtnStartCache.setEnabled(true);
                        mBtnEditPage.setEnabled(true);
                    }
                    mLock.unlock();
                    notifyListView();
                    break;
                case TASK_NEXT:
                    mLock.lock();
                    //判断当前页数是否超过指定的最终页
                    int page = mNav.getApiPagePar();
                    page++;
                    mNav.setApiPagePar(page);
                    //createNewEbookRequest();
                    if (mFinalPage == -1 || page <= mFinalPage) {
                        createNewEbookRequest();
                    } else {
                        sendEmptyMessage(TASK_BEGIN);
                    }
                    mTaskMsg.get(mTaskFinishedCount - 1).put("status", "缓存成功");
                    mTaskMsg.get(mTaskFinishedCount - 1).put("ebook", "");

                    mLock.unlock();
                    notifyListView();
                    break;
                case TASK_FINISH:
                    mLock.lock();
                    Bundle bundle = msg.getData();
                    mEbooks = bundle.getParcelableArrayList(KEY_EBOOK_INFO);

                    //显示提示文字
                    if (TextUtils.isEmpty(mTxtPrompt.getText())) {
                        EbookRequest ebookRequest = bundle.getParcelable(KEY_EBOOK_REQUEST_INFO);
                        if (ebookRequest!= null) {
                            int totalPage = 0;
                            mMaxPage = ebookRequest.getYe();
                            if (mFinalPage == -1) {
                                totalPage = mMaxPage - mInitPage + 1;
                            } else {
                                totalPage = mFinalPage - mInitPage + 1;
                            }
                            StringBuilder builder = new StringBuilder();
                            builder.append("正在缓存").append(mNav.getName())
                                    .append("，共")//.append(ebookRequest.getPagenum()).append("条数据，")
                                    .append(totalPage).append("页，")
                                    .append("每页").append(ebookRequest.getPagesize()).append("条数据");
                            mTxtPrompt.setText(builder);
                            mTxtPrompt.setVisibility(View.VISIBLE);

                        }
                    }

                    if (mTaskArray.size() >= mTaskFinishedCount) {

                        mTaskFinishedCount++;
                        if (mEbooks != null && mEbooks.size() > 0) {
                            //发送电子书下载请求
                            mHandler.sendEmptyMessage(TASK_EBOOK_INIT);
                        }else {
                            //没有电子书直接请求下一条数据
                            mHandler.sendEmptyMessage(TASK_NEXT);
                        }
                        notifyListView();
                    }
                    mLock.unlock();
                    break;
                /**
                 * 处理电子书、图片、目录等缓存服务
                 */
                case TASK_EBOOK_INIT:
                    mLock.lock();
                    if (mEbooks != null && mEbookTaskArray != null) {
                        mOneEbookTaskCount = mEbooks.size();
                        mEbookCurrentIndex = 0;
                        for (Ebook ebook : mEbooks) {
                            mEbookTaskCount++;
                            mEbookTaskArray.add(new EbookTask(ebook, mNav, mHandler));
                            updateEbookInitProgress();
                        }
                        mHandler.sendEmptyMessage(TASK_EBOOK_BEGIN);
                    }
                    mLock.unlock();
                    break;

                case TASK_EBOOK_BEGIN:
                    mLock.lock();
                    mEbookCurrentIndex = 0;
                    if (mEbookTaskArray.size() <= mEbookTaskCount &&
                            mEbookTaskArray.size() > mEbookTaskFinishedCount) {
                        mEbookTaskArray.get(mEbookTaskFinishedCount).execute();
                    }else {
                        mHandler.sendEmptyMessage(TASK_NEXT);
                    }
                    mLock.unlock();
                    break;

                case TASK_EBOOK_FINISH:
                    mLock.lock();
                    if (mEbookTaskArray.size() >= mEbookTaskFinishedCount) {
                        mEbookTaskFinishedCount++;

                        //String bookName = mEbooks.get(mEbookTaskFinishedCount % 16).getName();
                        String bookName = getBookName();
                        if (!TextUtils.isEmpty(bookName)) {
                            mTaskMsg.get(mTaskFinishedCount - 1)
                                    .put("ebook", "正在缓存《" + bookName + "》");
                        }
                        updateEbookFinishProgress();
                        mHandler.sendEmptyMessage(TASK_EBOOK_BEGIN);
                    }
                    mLock.unlock();
                    break;

                case TASK_OVER:
                    mLock.lock();
                    if (mTaskArray.size() >= mTaskFinishedCount) {
                        mTaskMsg.get(mTaskFinishedCount).put("status", "缓存完成");
                        mTaskFinishedCount++;
                        mHandler.sendEmptyMessage(TASK_BEGIN);
                    }
                    mLock.unlock();
                    notifyListView();
                    break;

                case TASK_OVER_WITH_NO_DATA:
                    mLock.lock();
                    if (mTaskArray.size() >= mTaskFinishedCount) {
                        mTaskMsg.get(mTaskFinishedCount).put("status", "没有数据");
                        mTaskFinishedCount++;
                        mHandler.sendEmptyMessage(TASK_BEGIN);
                    }
                    mLock.unlock();
                    notifyListView();
                    break;
                default:
            }
        }
    };
}
