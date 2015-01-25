package com.yxf.circleprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
   
public class CircleProgressBar extends View {
    
    private final static String TAG="CircleProgressView";
    private Context mContext; 
    private Paint mCompletePaint;
    private Paint mUncompletePaint;
    private Paint mTextPaint ;
    private Paint mFillPaint;
    private float mTextSize ; 
    private int mCompleteColor;
    private int mUncompleteColor;
    private int mTextColor;
    private int mFillColor;
    private int  mProgress; 
    private int  mMaxProgress;
    private float mFillBlockRaidus;
    private float mTotalRaidus;   
    private float mCX;
    private float mCY;
    private boolean isDrawText;      
    
    private final static int DEFAULT_COMPLETE_PAINT_COLOR=Color.parseColor("#78909C");
    private final static int DEFAULT_UNCOMPLETE_PAINT_COLOR=Color.parseColor("#CFD8DC");
    private final static int DEFAULT_TEXT_COLOR=Color.parseColor("#0091EA");
    private final static int DEFAULT_FILL_COLOR=Color.parseColor("#FAFAFA");
    private final static float MAX_SIZE=112; 
    private final static float MIN_SIZE=48; 
    
    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;     
       TypedArray attributes = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
       mCompleteColor=attributes.getColor(R.styleable.CircleProgressBar_progress_complete_color, DEFAULT_COMPLETE_PAINT_COLOR);
       mUncompleteColor=attributes.getColor(R.styleable.CircleProgressBar_progress_uncomplete_color, DEFAULT_UNCOMPLETE_PAINT_COLOR);
       mTextColor=attributes.getColor(R.styleable.CircleProgressBar_progress_text_color,DEFAULT_TEXT_COLOR);
       mFillBlockRaidus=attributes.getDimension(R.styleable.CircleProgressBar_progress_fill_radius, 0);
       mTextSize=attributes.getDimension(R.styleable.CircleProgressBar_progress_text_size, sptopx(12));
       mFillColor=attributes.getInt(R.styleable.CircleProgressBar_progress_fill_color, DEFAULT_FILL_COLOR);
       setMaxProgress(attributes.getInt(R.styleable.CircleProgressBar_progress_maxprogress, 100));
       setProgress(attributes.getInt(R.styleable.CircleProgressBar_progress_progress, 0));  
       isDrawText=attributes.getInt(R.styleable.CircleProgressBar_progress_text_visibility, 1)==1;
       attributes.recycle();  
       initializePainters();  
    }   
    private void initializePainters(){ 
        mCompletePaint=new Paint();
        mCompletePaint.setColor(mCompleteColor);
        mCompletePaint.setAntiAlias(true);
        
        mUncompletePaint=new Paint();
        mUncompletePaint.setColor(mUncompleteColor);
        mUncompletePaint.setAntiAlias(true);
        
        mFillPaint=new Paint();
        mFillPaint.setColor(mFillColor);
        mFillPaint.setAntiAlias(true);
        
        mTextPaint=new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize); 
        mTextPaint.setAntiAlias(true);
        
    }   
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);   
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int minSize = Math.min(widthSize, heightSize);
        int resultSize=0;   
        if(MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.AT_MOST||MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.AT_MOST){
            resultSize=(int)dptopx(MIN_SIZE);
        }else{
            if(minSize>=dptopx(MAX_SIZE)){
                resultSize=(int)dptopx(MAX_SIZE);
            }else if(minSize<=dptopx(MIN_SIZE)){
                resultSize=(int)dptopx(MIN_SIZE);
            }else{   
                resultSize=minSize;
            }   
        }
        setMeasuredDimension(resultSize, resultSize);   
    }  
    
   private void  calcuateBeforeDraw(){  
           mCX=getMeasuredWidth()/2;      
           mCY=getMeasuredHeight()/2;   
           mTotalRaidus=getMeasuredWidth()/2;
           if(mFillBlockRaidus<=0||mFillBlockRaidus>=mTotalRaidus){
               mFillBlockRaidus=mTotalRaidus*0.8f;
               Log.w(TAG, "fillblockraidus is inconformity " +mFillBlockRaidus);
           }    
           // change textsize 
         while( mTextPaint.measureText("100%")>=mFillBlockRaidus*2){
             mTextPaint.setTextSize(mTextPaint.getTextSize()-2);   
         }  
   }        
   
    @Override
    protected void onDraw(Canvas canvas){ 
        super.onDraw(canvas); 
        if(mProgress>mMaxProgress){
            Log.w(TAG, "progress exceed maxprogess ----");
            mProgress=mMaxProgress;
        }   
        calcuateBeforeDraw();   
        drawUnCompleteCircle(canvas);
        drawCompleteCircle(canvas);
        drawFillCircleBlock(canvas);
        if(isDrawText)drawCenterText(canvas); 
           
    }    
    private void drawUnCompleteCircle(Canvas canvas){
        canvas.drawCircle(mCX, mCY, mTotalRaidus, mUncompletePaint);
    }   
   private void drawCompleteCircle(Canvas canvas){
       RectF oval=new RectF(0, 0, getWidth(), getHeight());
       float sweepAngle=360*(getProgress()/(getMaxProgress()*1.0f));
       canvas.drawArc(oval, -90, sweepAngle, true,mCompletePaint); //从三点钟开始
   }    
    private void drawFillCircleBlock(Canvas canvas){
        canvas.drawCircle(mCX  , mCY, mFillBlockRaidus, mFillPaint);
    }       
    private void drawCenterText(Canvas canvas){ 
        String text=(int)(100*((getProgress()/(1.0f*getMaxProgress()))))+"%";
        Rect textBound=new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBound);
        float textwidth = mTextPaint.measureText(text); 
        float textheight=textBound.height();
        canvas.drawText(text, mCX-(textwidth/2.0f), mCY+(textheight/2.0f), mTextPaint); 
    }       
    public void setMaxProgress(int maxProgress){
          this.mMaxProgress=Math.abs(maxProgress);
    }           
    public int getMaxProgress(){
        return mMaxProgress;
    }   
    public void setProgress(int progress){
          this.mProgress=progress;    
          if(mProgress>mMaxProgress||mProgress<0){
              return ;
          }  
          invalidate();
    }  
    public int getProgress(){
        return mProgress;  
    }   
    public void setCompleteColor(int color){
        mCompleteColor=color;
        invalidate();
    }   
    public void setUnCompleteColor(int color){
        mUncompleteColor=color;
        invalidate();
    }   
    public void setTextColor(int color){
        mTextColor=color;
        invalidate();
    }   
    private float dptopx(float dp){
       float scale = getResources().getDisplayMetrics().density;
       return scale*dp+0.5f;
    }   
    private float sptopx(float sp ){
        float scale=getResources().getDisplayMetrics().scaledDensity;
        return scale*sp;
    }
    
}
