package com.hoangdevelopers.listen_and_write.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoangdevelopers.listen_and_write.R;
import com.hoangdevelopers.listen_and_write.models.WordQuiz;
import com.wefika.flowlayout.FlowLayout;


public class InputAdapter {
    private ViewGroup view;
    private TextView textView;
    private Context context;
    private WordQuiz wordQuiz;

    public OnTextChanged getOnTextChanged() {
        return onTextChanged;
    }

    public void setOnTextChanged(OnTextChanged onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    public interface OnTextChanged {
        void run(String str);
    }

    private OnTextChanged onTextChanged;

    public InputAdapter(Context _context, WordQuiz _wordQuiz) {
        context = _context;
        wordQuiz = _wordQuiz;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = (ViewGroup) layoutInflater.inflate(R.layout.input, null);
        view.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT));
        textView = (TextView) view.getChildAt(0);
    }

    public View getView() {
        return view;
    }

    public synchronized String type(String s) {
        textView.setText((String) textView.getText() + s);
        onChange((String) textView.getText());
        return (String) textView.getText();
    }

    public synchronized String pressDelete() {
        String str = (String) textView.getText();
        if (str.length() == 0) {
            return (String) textView.getText();
        }
        textView.setText(str.substring(0, str.length() - 1));
        onChange((String) textView.getText());
        return (String) textView.getText();
    }

    private void onChange(String str) {
        if (str.length() == 0) {
            textView.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else if (str.equals(wordQuiz.getWordNormalize())) {
            textView.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else if (wordQuiz.getWordNormalize().indexOf(str) == 0) {
            textView.setTextColor(context.getResources().getColor(R.color.colorYellow));
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.colorRed));
        }
        onTextChanged.run(str);
    }

    public WordQuiz getWordQuiz() {
        return wordQuiz;
    }
}
