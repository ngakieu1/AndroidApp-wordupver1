package com.example.wordup.ShareHepler;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class ShareHelper {

    private final Context context;

    public ShareHelper(Context context) {
        this.context = context;
    }

    public void shareScore(String playerName, int score) {
        String message = "Tôi là " + playerName + " và tôi đã đạt được " + score + " điểm trong WordUp! 🔥\n"
                + "Tải game ngay: https://play.google.com/store/apps/details?id=com.example.wordup";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // Facebook kiểm soát chặt chẽ nên nếu không dùng SDK thì mặc định sẽ hiện các app khác có thể chia sẻ
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(shareIntent, "Chia sẻ thành tích của bạn"));
        } catch (Exception e) {
            Toast.makeText(context, "Không thể chia sẻ vào lúc này.", Toast.LENGTH_SHORT).show();
        }
    }

    // Nếu bạn có URL avatar hoặc muốn chia sẻ ảnh:
    public void shareScoreWithImage(String playerName, int score, Uri imageUri) {
        String message = "Tôi là " + playerName + " và tôi đã đạt " + score + " điểm! 💯";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            context.startActivity(Intent.createChooser(shareIntent, "Chia sẻ thành tích của bạn"));
        } catch (Exception e) {
            Toast.makeText(context, "Không thể chia sẻ hình ảnh vào lúc này.", Toast.LENGTH_SHORT).show();
        }
    }
}