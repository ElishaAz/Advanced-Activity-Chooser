package com.elishaazaria.advancedactivitychooser.tools;

import java.util.HashMap;
import java.util.List;

public class MimeTypeTools {
    /**
     * All mime types by category. Taken from: https://www.sitepoint.com/mime-types-complete-list/
     */
    public static final HashMap<String, String[]> mimeTypes = new HashMap<String, String[]>() {
        {
            put("application", new String[]{"acad", "arj", "base64", "binhex", "binhex4", "book", "cdf", "clariscad", "commonground", "drafting", "dsptype", "dxf", "ecmascript", "envoy", "excel", "fractals", "freeloader", "futuresplash", "gnutar", "groupwise", "hlp", "hta", "i-deas", "iges", "inf", "java", "java-byte-code", "javascript", "lha", "lzx", "mac-binary", "mac-binhex", "mac-binhex40", "mac-compactpro", "macbinary", "marc", "mbedlet", "mcad", "mime", "mspowerpoint", "msword", "mswrite", "netmc", "octet-stream", "oda", "pdf", "pkcs-12", "pkcs-crl", "pkcs10", "pkcs7-mime", "pkcs7-signature", "pkix-cert", "pkix-crl", "plain", "postscript", "powerpoint", "pro_eng", "ringing-tones", "rtf", "sdp", "sea", "set", "sla", "smil", "solids", "sounder", "step", "streamingmedia", "toolbook", "vda", "vnd.fdf", "vnd.hp-hpgl", "vnd.hp-pcl", "vnd.ms-excel", "vnd.ms-pki.certstore", "vnd.ms-pki.pko", "vnd.ms-pki.seccat", "vnd.ms-pki.stl", "vnd.ms-powerpoint", "vnd.ms-project", "vnd.nokia.configuration-message", "vnd.nokia.ringing-tone", "vnd.rn-realmedia", "vnd.rn-realplayer", "vnd.wap.wmlc", "vnd.wap.wmlscriptc", "vnd.xara", "vocaltec-media-desc", "vocaltec-media-file", "wordperfect", "wordperfect6.0", "wordperfect6.1", "x-123", "x-aim", "x-authorware-bin", "x-authorware-map", "x-authorware-seg", "x-bcpio", "x-binary", "x-binhex40", "x-bsh", "x-bytecode.elisp (compiled elisp)", "x-bytecode.python", "x-bzip", "x-bzip2", "x-cdf", "x-cdlink", "x-chat", "x-cmu-raster", "x-cocoa", "x-compactpro", "x-compress", "x-compressed", "x-conference", "x-cpio", "x-cpt", "x-csh", "x-deepv", "x-director", "x-dvi", "x-elc", "x-envoy", "x-esrehber", "x-excel", "x-frame", "x-freelance", "x-gsp", "x-gss", "x-gtar", "x-gzip", "x-hdf", "x-helpfile", "x-httpd-imap", "x-ima", "x-internett-signup", "x-inventor", "x-ip2", "x-java-class", "x-java-commerce", "x-javascript", "x-koan", "x-ksh", "x-latex", "x-lha", "x-lisp", "x-livescreen", "x-lotus", "x-lotusscreencam", "x-lzh", "x-lzx", "x-mac-binhex40", "x-macbinary", "x-magic-cap-package-1.0", "x-mathcad", "x-meme", "x-midi", "x-mif", "x-mix-transfer", "x-mplayer2", "x-msexcel", "x-mspowerpoint", "x-navi-animation", "x-navidoc", "x-navimap", "x-navistyle", "x-netcdf", "x-newton-compatible-pkg", "x-nokia-9000-communicator-add-on-software", "x-omc", "x-omcdatamaker", "x-omcregerator", "x-pagemaker", "x-pcl", "x-pixclscript", "x-pkcs10", "x-pkcs12", "x-pkcs7-certificates", "x-pkcs7-certreqresp", "x-pkcs7-mime", "x-pkcs7-signature", "x-pointplus", "x-portable-anymap", "x-project", "x-qpro", "x-rtf", "x-sdp", "x-sea", "x-seelogo", "x-sh", "x-shar", "x-shockwave-flash", "x-sit", "x-sprite", "x-stuffit", "x-sv4cpio", "x-sv4crc", "x-tar", "x-tbook", "x-tcl", "x-tex", "x-texinfo", "x-troff", "x-troff-man", "x-troff-me", "x-troff-ms", "x-troff-msvideo", "x-ustar", "x-visio", "x-vnd.audioexplosion.mzz", "x-vnd.ls-xpix", "x-vrml", "x-wais-source", "x-winhelp", "x-wintalk", "x-world", "x-wpwin", "x-wri", "x-x509-ca-cert", "x-x509-user-cert", "x-zip-compressed", "xml", "zip"});
            put("audio", new String[]{"aiff", "basic", "it", "make", "make.my.funk", "mid", "midi", "mod", "mpeg", "mpeg3", "nspaudio", "s3m", "tsp-audio", "tsplayer", "vnd.qcelp", "voc", "voxware", "wav", "x-adpcm", "x-aiff", "x-au", "x-gsm", "x-jam", "x-liveaudio", "x-mid", "x-midi", "x-mod", "x-mpeg", "x-mpeg-3", "x-mpequrl", "x-nspaudio", "x-pn-realaudio", "x-pn-realaudio-plugin", "x-psid", "x-realaudio", "x-twinvq", "x-twinvq-plugin", "x-vnd.audioexplosion.mjuicemediafile", "x-voc", "x-wav", "xm"});
            put("chemical", new String[]{"x-pdb"});
            put("drawing", new String[]{"x-dwf"});
            put("i-world", new String[]{"i-vrml"});
            put("image", new String[]{"bmp", "cmu-raster", "fif", "florian", "g3fax", "gif", "ief", "jpeg", "jutvision", "naplps", "pict", "pjpeg", "png", "tiff", "vasa", "vnd.dwg", "vnd.fpx", "vnd.net-fpx", "vnd.rn-realflash", "vnd.rn-realpix", "vnd.wap.wbmp", "vnd.xiff", "x-cmu-raster", "x-dwg", "x-icon", "x-jg", "x-jps", "x-niff", "x-pcx", "x-pict", "x-portable-anymap", "x-portable-bitmap", "x-portable-graymap", "x-portable-greymap", "x-portable-pixmap", "x-quicktime", "x-rgb", "x-tiff", "x-windows-bmp", "x-xbitmap", "x-xbm", "x-xpixmap", "x-xwd", "x-xwindowdump", "xbm", "xpm"});
            put("message", new String[]{"rfc822"});
            put("model", new String[]{"iges", "vnd.dwf", "x-pov"});
            put("multipart", new String[]{"x-gzip", "x-ustar", "x-zip"});
            put("music", new String[]{"crescendo", "x-karaoke"});
            put("paleovu", new String[]{"x-pv"});
            put("text", new String[]{"asp", "css", "ecmascript", "html", "javascript", "mcf", "pascal", "plain", "richtext", "scriplet", "sgml", "tab-separated-values", "uri-list", "vnd.abc", "vnd.fmi.flexstor", "vnd.rn-realtext", "vnd.wap.wml", "vnd.wap.wmlscript", "webviewhtml", "x-asm", "x-audiosoft-intra", "x-c", "x-component", "x-fortran", "x-h", "x-java-source", "x-la-asf", "x-m", "x-pascal", "x-script", "x-script.csh", "x-script.elisp", "x-script.guile", "x-script.ksh", "x-script.lisp", "x-script.perl", "x-script.perl-module", "x-script.phyton", "x-script.rexx", "x-script.scheme", "x-script.sh", "x-script.tcl", "x-script.tcsh", "x-script.zsh", "x-server-parsed-html", "x-setext", "x-sgml", "x-speech", "x-uil", "x-uuencode", "x-vcalendar", "xml"});
            put("video", new String[]{"animaflex", "avi", "avs-video", "dl", "fli", "gl", "mpeg", "msvideo", "quicktime", "vdo", "vivo", "vnd.rn-realvideo", "vnd.vivo", "vosaic", "x-amt-demorun", "x-amt-showrun", "x-atomic3d-feature", "x-dl", "x-dv", "x-fli", "x-gl", "x-isvideo", "x-motion-jpeg", "x-mpeg", "x-mpeq2a", "x-ms-asf", "x-ms-asf-plugin", "x-msvideo", "x-qtc", "x-scm", "x-sgi-movie"});
            put("windows", new String[]{"metafile"});
            put("www", new String[]{"mime"});
            put("x-conference", new String[]{"x-cooltalk"});
            put("x-music", new String[]{"x-midi"});
            put("x-world", new String[]{"x-3dmf", "x-svr", "x-vrml", "x-vrt"});
            put("xgl", new String[]{"drawing", "movie"});
        }
    };
}
