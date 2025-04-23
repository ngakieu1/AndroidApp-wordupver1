package com.example.wordup.ShareHepler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class FacebookShareHelper {

    /**
     * Chia sẻ ảnh và văn bản qua bất kỳ ứng dụng nào hỗ trợ (Facebook, Zalo, Messenger,...)
     */
    public static void shareImageAndText(Context context, Uri imageUri, String text) {
        if (imageUri == null) {
            Toast.makeText(context, "Không thể chia sẻ vì không có ảnh.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Mở ra tất cả app có thể chia sẻ
            context.startActivity(Intent.createChooser(shareIntent, "Chia sẻ với:"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Không thể chia sẻ nội dung.", Toast.LENGTH_LONG).show();
        }
    }
}
