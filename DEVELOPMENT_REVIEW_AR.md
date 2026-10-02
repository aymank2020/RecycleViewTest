# مراجعة RecycleViewTest وتأسيس مثال Android جديد

تاريخ التنفيذ: 2026-10-02. المصدر: [aymank2020/RecycleViewTest](https://github.com/aymank2020/RecycleViewTest).
الفرع: `codex/review-develop-2026-10-02`. أساس المرحلة الثانية:
`b2e0d27e1619ae2a24c6a7aa4d7c04d7c8ec0a98`، بعد مراجعة الاستعادة الأولى.

## نتيجة البحث عن الأصل

راجع البحث الفروع والوسوم المعلنة عبر `git ls-remote`، وGitHub metadata
والتاريخ وgit tree. المستودع ليس fork ولا يحمل parent/source upstream.
master له commit أصلي واحد في 2019:
`9dd962ee830a8812e19c4f83574f5ae492db73ac`؛ يحتوي7ملفات إعداد فقط، بلا
app أو wrapper كامل. لا tags ولا fork مصدر آخر ظهر في metadata. الفرع
الإضافي الوحيد هو فرع المراجعة الحالية. ملف MyLogin.iml يعرّف هيكل IDE
بلا محتوى تطبيق، ولا يوفر مصدرًا يمكن استعادته.

**لم يُستعد التطبيق الأصلي.** نُفذت مرحلة تأسيس جديدة معلنة في README، وفق
وصف المستودع الأصلي: RecyclerView أفقي وشبكة صور. لا تُنسب الصور أو الكود
الجديد إلى مصدر مفقود، ولا يُدّعى أن التاريخ العام يشمل نسخًا خاصة غير متاحة.

## الخطة المنفذة

1. app module جديد باسم package مستقل `net.aymanx.examples.recycleview`،
   وMainActivity مسجلة بوصفها نقطة MAIN/LAUNCHER؛ عنوانها «معرض الأشكال».
2. صف أفقي بـLinearLayoutManager وشبكة بـGridLayoutManager بعمودين، أو
   ثلاثة عند عرض600dp. ثمانية أشكال تُرسم محليًا بـCanvas بألوان وأسماء
   عربية؛ لا صور خارجية أو APIs أو حسابات أو أذونات شبكة/تخزين.
3. اختيار واحد مشترك بين العرضين، وهوية ثابتة للعناصر، وحراسة NO_POSITION
   عند النقر. binding يعيد رسم الصورة والاسم وحالة التحديد لكل صف معاد استخدامه.
4. زر إخفاء/عرض يجعل حالة القائمة الفارغة مرئية دون مضاعفة العناصر. تُحفظ
   حالة الاختيار والإخفاء في savedInstanceState، ويُفصل adapter عن العرضين
   عند تدمير Activity. أوصاف الوصول تميز العنصر المحدد.
5. إعادة إنشاء Wrapper5.6.4 من التوزيع الرسمي المتحقق منه، مع SHA256 مثبت
   لتنزيل bin. اختيار AGP3.5.1 وSDK29 وBuild-Tools29.0.2 وRecyclerView1.0.0
   وCore1.0.1، وفق البيئة التي نجحت بالفعل مع Contacts-ALL. Maven Central
   بعد Google، مع خيار Maven محلي صريح للبناء offline.

MyLogin.iml القديم أُزيل من التتبع وأُبقي محليًا؛ فهو إعداد IDE باسم مشروع
قديم لا يستهلكه تطبيق التأسيس. local.properties ما زال محليًا ومتجاهلًا.
لا APK أو cache أو مسارات جهاز أُضيفت إلى Git. الـwrapper وملفات مصادر التطبيق
والموارد والاختبارات موصولة بأوامر البناء ونقطة التشغيل الفعلية.

## التحقق والأثر

طُبقت integration-impact-review بعد مراجعة الكود والاختبارات. المسار الحقيقي:
Manifest/MAIN → MainActivity → LayoutManagers وPhotoAdapter → PhotoCatalog/
GalleryState → ShapeTileView والصف/الشبكة وحالة الاختيار. المستهلك مستخدم
مثال المعرض المحلي؛ الأثر المقصود رؤية الشكل نفسه وتحديده في عرضين.

نجح `assembleDebug testDebugUnitTest` **بوضع offline** في مجلد بناء مستقل
`D:/Codex-GitHub-Review-20261002/builds/RecycleViewTest`، بكاش Gradle خاص
بالمراجعة على D. لم يُستخدم القرص E لمخرجات Android، ولم تُنزّل artifacts
جديدة. نُسخ كاش انتقائي268MiB، ومُرآة Maven محلية24MiB من ملفات الكاش
الموجودة بعد التحقق من بصماتها؛ لم يُعدَّل كاش E المشترك.

الأمر الفعلي استعمل Gradle5.6.4 المباشر المتحقق منه مع `--offline --no-daemon
--max-workers=2` و`-PofflineMavenRepository=...`، وJDK8u504 وANDROID_HOME
إلى SDK الموجود. heap1GB، وtest heap128MB. السجل النهائي:
`evidence/recycle-foundation-build-validated.log`، والنتيجة BUILD SUCCESSFUL
في35ثانية،29مهمة. Wrapper وملف checksum وُلدت رسميًا؛ لم تُعد تجربة تنزيل
توزيع wrapper لأن التوزيع نفسه موجود ومتحقق منه.

اجتازت **7اختبارات JUnit**: هوية8عناصر و4أنواع أشكال، استعادة الاختيار،
الإخفاء/الإظهار المتكرر دون تكرار، fallback لمعرف قديم، رفض اختيار غير صالح
أو مخفي، ومنع تعديل الكتالوج. نتيجة XML: failures0 وerrors0.
هذه اختبارات حالة وبيانات، وليست محاكاة لمسّ Android أو تدوير شاشة حقيقية.

فحص APK الفعلي بـaapt أكد package وmin21 وtarget29 وعنوان التطبيق وMainActivity
المسجلة، ووجود عرضَي RecyclerView في XML المجمّع، وعدم وجود uses-permission.
فحص تعريفات classes في ملفَي DEX أكد وجود MainActivity وPhotoAdapter
وShapeTileView وGalleryState وPhotoCatalog، وبصمات مصادر app في نسخة D تطابق
نسخة Git في E. الدليل `evidence/recycle-foundation-result.json`، مع
`recycle-apk-badging.txt` و`recycle-apk-layout.txt`. الفحص الهيكلي PowerShell
وXML و`git diff --check` اجتازت أيضًا.

APK محلي: `D:/Codex-GitHub-Review-20261002/builds/RecycleViewTest/app/build/outputs/apk/debug/app-debug.apk`.
الحجم724707بايت، SHA256:
`1ea7892cf8392d52f0c083a3f45de1b465567439833cb3ade75abaaf51c5bcf5`.

## الفجوات والمراحل التالية

`adb devices` لم يجد جهازًا. لذلك لم يُتحقق تفاعليًا من النقر والتمرير
والـRTL والتدوير أو TalkBack؛ نجاح APK والاختبارات لا يُعد نجاح UI على جهاز.
لم يُشغّل محاكي ثقيل في البيئة محدودة المساحة والذاكرة. توجد تحذيرات SDK XML
وأدوات Gradle القديمة، ولا يُقدَّم toolchain الحالي باعتباره تحديث إنتاج حديثًا.

1. تشغيل APK على جهاز API21 وآخر حديث: الاختيار من الصف والشبكة، التمرير،
   إخفاء/عرض مرتين، تدوير الشاشة، وإعادة إنشاء Activity مع اختبار الحالة المخفية.
2. اختبار الإتاحة وRTL والشاشات الضيقة، ثم تحسين حفظ موضع التمرير إن ظهر
   خلل في فحص الجهاز. لا تُضاف اختبارات تزعم سلوكًا لم يُشغّل فعليًا.
3. نقل المثال إلى أدوات Android مدعومة حديثًا على مرحلة مستقلة، مع حفظ
   اختبارات الهوية والحالة والسلوك، بدل ترقية اعتماديات كثيرة دون تحقق.

## مصادر أولية

- [Android: RecyclerView وAdapter ومديرو الصف والشبكة](https://developer.android.com/develop/ui/views/layout/recyclerview)
- [Gradle: Wrapper وفحص تنزيل التوزيع](https://docs.gradle.org/current/userguide/gradle_wrapper.html)
- [SHA256 الرسمي لتوزيع Gradle5.6.4-bin](https://downloads.gradle.org/distributions/gradle-5.6.4-bin.zip.sha256)
