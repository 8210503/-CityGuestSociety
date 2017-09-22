package www.cityguestsociety.com.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LuoPan on 2017/9/20 11:46.
 */

public class EmojiUtils {
    static String string;

    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (buf == null) {
                buf = new StringBuilder(source.length());
            }
            if (!isEmojiCharacter(codePoint)) {
                string = String.valueOf(codePoint);
            } else {
                try {
                    StringBuilder builder = new StringBuilder(2);
                    byte[] str = builder.append(String.valueOf(codePoint)).append(String.valueOf(source.charAt(i + 1))).toString().getBytes("UTF-8");
                    String strin = Arrays.toString(str);
                    String newString = strin.substring(1, strin.length() - 1);
                    string = "Γ" + newString + "Γ";
                    System.out.println("filters running newStr = " + string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
            buf.append(string + "⅞");
        }
        if (buf == null) {
            return "";
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                System.out.println("filter running buf.toString() = " + buf.toString());
                String bufStr = buf.toString();
                String newBufStr = bufStr.substring(0, bufStr.length() - 1);
                return newBufStr;
            }
        }
    }

    // 判别是否包含Emoji表情
    private static boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String getEmotionContent(final Context context, final TextView tv, String source) {
      /*  String string = new String();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(source);

        while (matcherEmotion.find()) {
            String str = matcherEmotion.group(0);
            int index = Integer.parseInt(str.substring(str.indexOf("(")+1, str.indexOf(")")));
            // 输入表情
            ImageSpan imageSpan = new ImageSpan(mcontext, BitmapFactory.decodeResource(context.getResources(),(AppData.getweiBoface())[index]));
            spannableString.setSpan(imageSpan, matcher.start(0), matcher.end(0), SpannableString.SPAN_MARK_MARK);

        }
        */
        return string;
    }

    private static String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    //得到服务器的数据之后进行解析，显示在UI上
    String newsString;

    public void setString(TextView textView, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String arrays[] = string.split("⅞");
        for (int j = 0; j < arrays.length; j++) {
            System.out.println("filter running arrays[] = " + arrays[j]);
            String ss = arrays[j];
            char char_ss = ss.charAt(0);
            System.out.println("filter running String.valueOf(char_ss) = " + String.valueOf(char_ss));
            if (String.valueOf(char_ss).equals("Γ")) {
                String new_SS = ss.substring(1, ss.length() - 1);
                String strArrays[] = new_SS.split(", ");
                byte[] chars = new byte[strArrays.length];
                for (int i = 0; i < strArrays.length; ++i) {
                    System.out.println("strArrays[i]:" + strArrays[i]);
                    chars[i] = Byte.decode(strArrays[i]);
                }
                newsString = new String(chars);
            } else {
                newsString = ss;
            }
            stringBuilder.append(newsString);
            System.out.println("filter running stringBuilder.toString() = " + stringBuilder.toString());
            textView.setText(stringBuilder.toString());
        }
    }
}
