package pvt.example.sophon.utils;

import catcode.CatCodeUtil;
import catcode.CodeTemplate;
import catcode.MutableNeko;
import catcode.Neko;

/**
 * 类&emsp;&emsp;名：TestUtils <br/>
 * 描&emsp;&emsp;述：
 */
public class TestUtils {

    public static final CatCodeUtil UTILS = CatCodeUtil.getInstance();
    private static final CodeTemplate<Neko> NEKO_TEMPLATE = UTILS.getNekoTemplate();

    private TestUtils() {
    }

    public static Neko getRecord(String path) {
        MutableNeko neko = NEKO_TEMPLATE.record(path).mutable();
        neko.setType("voice");
        return neko.immutable();
    }
}
