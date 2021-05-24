package co.com.univision.widgets.deserialization;

import co.com.univision.widgets.domain.WidgetDTO;
import co.com.univision.widgets.domain.ContentByTypeDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class WidgetDeserializerTest {


    public static final String SOURCE_JSON = "{\"status\":\"success\"," +
            "\"data\":{\"status\":\"success\"," +
            "\"widgets\":[" +
            "{\"settings\":{\"highlightedCompetitionSeasons\":[],\"title\":\"SCORECELLS GENÉRICOS\",\"titleLink\":null,\"type\":\"deportescardsoccermatchscorecells\",\"uid\":\"00000167-eb82-dddd-afff-ff826dab0000\",\"lazyLoaded\":false},\"id\":\"00000167-eb82-dddd-afff-ff826dab0000\",\"type\":\"DeportesCardSoccerMatchScorecells\",\"contents\":[]}," +
            "{\"settings\":{\"type\":\"allgridfiveitemsleadtop\",\"uid\":\"00000169-bbef-d10d-a96f-fbff48c10002\",\"title\":null,\"titleLink\":null,\"lazyLoaded\":false},\"id\":\"00000169-bbef-d10d-a96f-fbff48c10002\",\"type\":\"AllGridFiveItemsLeadTop\"," +
            "\"contents\":[" +
            "{\"type\":\"soccermatch\",\"uid\":\"00000167-c944-d6ad-a5ff-ff5eba6e0000\",\"uri\":\"/deportes/futbol/liga-mx-clausura/pachuca-vs-atlas-liga-mx-clausura-2019-04-27\",\"vertical\":null,\"parent\":null,\"title\":\"EN VIVO | El VAR ya es protagonista; dos rojas y un gol anulado\",\"description\":\"Los Tuzos reciben en casa a los Rojinegros donde con un empate les es suficiente para asegurar Liguilla.\",\"image\":{\"type\":\"image\",\"uid\":\"0000016a-60c4-d08a-abff-6ef45c590000\",\"title\":\"EN VIVO | Pachuca vs. Atlas\",\"caption\":null,\"credit\":\"Imago 7\",\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/fe/e7/d2965d1b4f9b9a6107c934a32284/0-0.jpg\",\"width\":1280,\"height\":720},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/860f960/2147483647/thumbnail/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2Fresizes%2F500%2F0-0.jpg\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/2d67eb2/2147483647/thumbnail/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2F0-0.jpg\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/e1b0e66/2147483647/thumbnail/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2Fresizes%2F500%2F0-0.jpg\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/7ebdf5f/2147483647/thumbnail/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2Fresizes%2F500%2F0-0.jpg\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/34d80bf/2147483647/thumbnail/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2F0-0.jpg\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/08ac4fa/2147483647/thumbnail/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2F0-0.jpg\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/2d80bab/2147483647/thumbnail/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Ffe%2Fe7%2Fd2965d1b4f9b9a6107c934a32284%2Fresizes%2F500%2F0-0.jpg\",\"width\":30,\"height\":17}}},\"authors\":[],\"updateDate\":\"2019-04-27T18:34:25-04:00\",\"publishDate\":\"2019-01-02T11:20:05-05:00\",\"expirationDate\":\"3000-01-01T00:00:00-05:00\"}]}," +
            "{\"settings\":{\"type\":\"allgridfiveitemsleadleft\",\"uid\":\"00000167-eb82-dddd-afff-ff82443d000b\",\"title\":null,\"titleLink\":null,\"lazyLoaded\":false},\"id\":\"00000167-eb82-dddd-afff-ff82443d000b\",\"type\":\"AllGridFiveItemsLeadLeft\"," +
            "\"contents\":[" +
            "{\"type\":\"article\",\"uid\":\"0000016a-609d-d0b8-a37a-fafde3fa0002\",\"uri\":\"/noticias/tiroteos/al-menos-un-muerto-y-tres-heridos-tras-tiroteo-en-un-tiroteo-en-una-sinagoga-en-california\",\"vertical\":\"Noticias\",\"parent\":{\"uri\":\"/noticias/tiroteos\",\"name\":\"tiroteos\",\"title\":\"Tiroteos\"},\"title\":\"\\\"Fue un crimen de odio\\\": un muerto y 3 heridos tras tiroteo en una sinagoga en California\",\"description\":\"Una persona fue detenida y es interrogada por la policía y el FBI. Los heridos fueron trasladados a un centro médico cercano y se encuentran fuera de peligro. Uno de ellos es una niña.\",\"image\":{\"type\":\"image\",\"uid\":\"0000016a-60c3-d08a-abff-6ef38fb90000\",\"title\":\"sinagoga_ap.jpeg\",\"caption\":\"Dos personas se abrazan a las afueras de la sinagoga Chabad, en la ciudad de Poway, donde este sábado ocurrió un tiroteo.\",\"credit\":\"Denis Poroy/AP\",\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/a8/63/aec84b6149fa93a1e83f61d04d99/sinagoga-ap.jpeg\",\"width\":2000,\"height\":1338},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/5290d0e/2147483647/thumbnail/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F500%2Fsinagoga-ap.jpeg\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/c35ad46/2147483647/thumbnail/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F1500%2Fsinagoga-ap.jpeg\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/6dde560/2147483647/thumbnail/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F500%2Fsinagoga-ap.jpeg\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/a85c9f6/2147483647/thumbnail/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F500%2Fsinagoga-ap.jpeg\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/089ff8f/2147483647/thumbnail/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F1500%2Fsinagoga-ap.jpeg\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/fc33f25/2147483647/thumbnail/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F1500%2Fsinagoga-ap.jpeg\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/909c6b9/2147483647/thumbnail/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2Fa8%2F63%2Faec84b6149fa93a1e83f61d04d99%2Fresizes%2F500%2Fsinagoga-ap.jpeg\",\"width\":30,\"height\":17}}},\"authors\":[],\"updateDate\":\"2019-04-27T18:21:40-04:00\",\"publishDate\":\"2019-04-27T17:30:49-04:00\",\"expirationDate\":null,\"contentPriority\":\"STANDARD\",\"leadType\":\"image\",\"primaryTag\":{\"name\":\"Tiroteos\",\"link\":\"/noticias/tiroteos\"}}," +
            "{\"type\":\"slideshow\",\"uid\":\"0000016a-5c7f-d0b8-a37a-feff585d0001\",\"uri\":\"/famosos/tras-el-suicidio-de-su-esposa-demian-bichir-amenaza-a-la-prensa-con-acciones-legales-si-no-respetan-su-privacidad-fotos\",\"vertical\":\"Entretenimiento\",\"parent\":{\"uri\":\"/famosos\",\"name\":\"famosos\",\"title\":\"Famosos\"},\"title\":\"Tras el suicidio de su esposa, Demián Bichir amenaza a la prensa con acciones legales si no respetan su privacidad\",\"description\":\"Luego que dos reporteros se presentaran en su casa a buscar una declaración, el actor mexicano enfatizó que Instagram es el único medio por el que emitirá comentarios sobre la muerte de su esposa, Stefanie Sherk, y si la prensa no respeta su privacidad, tomará acciones legales.\",\"image\":{\"type\":\"image\",\"uid\":\"0000016a-5c7f-d0b8-a37a-feff58a20001\",\"title\":\"bichir_260419_a.jpg\",\"caption\":null,\"credit\":null,\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/4f/38/23cc8637462796f0a981500316f9/bichir-260419-a.jpg\",\"width\":1920,\"height\":1080},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/3f3fef6/2147483647/thumbnail/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F500%2Fbichir-260419-a.jpg\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/5ddd1ef/2147483647/thumbnail/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F1500%2Fbichir-260419-a.jpg\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/aac42bb/2147483647/thumbnail/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F500%2Fbichir-260419-a.jpg\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/e7cbce9/2147483647/thumbnail/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F500%2Fbichir-260419-a.jpg\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/9ff4cc2/2147483647/thumbnail/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F1500%2Fbichir-260419-a.jpg\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/0d442ae/2147483647/thumbnail/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F1500%2Fbichir-260419-a.jpg\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/4d708c1/2147483647/thumbnail/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F4f%2F38%2F23cc8637462796f0a981500316f9%2Fresizes%2F500%2Fbichir-260419-a.jpg\",\"width\":30,\"height\":17}}},\"authors\":[],\"updateDate\":\"2019-04-26T22:59:56-04:00\",\"publishDate\":\"2019-04-26T22:59:56-04:00\",\"expirationDate\":null,\"contentPriority\":\"STANDARD\",\"primaryTag\":{\"name\":\"Famosos\",\"link\":\"/famosos\"}}," +
            "{\"type\":\"article\",\"uid\":\"0000016a-4af9-d3f6-a9fe-eeff28a80002\",\"uri\":\"/noticias/politica/hay-razones-para-llevar-a-juicio-politico-a-trump-asi-es-el-delicado-camino-hacia-un-impeachment\",\"vertical\":\"Noticias\",\"parent\":{\"uri\":\"/noticias/politica\",\"name\":\"política\",\"title\":\"Política\"},\"title\":\"¿Hay razones para llevar a juicio político a Trump? Así es el delicado camino hacia un 'impeachment'\",\"description\":\"<b>&quot;Si cualquier otro ser humano en este país hubiera hecho lo que está documentado en el informe Mueller, sería arrestado y encarcelado&quot;</b>, dijo la candidata presidencial Elizabeth Warren, una de las voces a favor de enjuiciar al presidente políticamente. El &#39;impeachment&#39; es una de las herramientas más poderosas y delicadas de la Constitución. \",\"image\":{\"type\":\"image\",\"uid\":\"0000016a-4bc6-d3f6-a9fe-efee41380000\",\"title\":\"composicion_getty.jpg\",\"caption\":\"Tras la publicación del reporte de Mueller, los demócratas se debaten frente a la posibilidad del \\n<i>impeachment</i> al presidente Donald Trump.\",\"credit\":\"Composición con imágenes de Getty\",\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/7f/0a/0474a93a46bf818cfa1a16fe0a84/composicion-getty.jpg\",\"width\":3000,\"height\":1688},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/eaed1e2/2147483647/crop/499x281%2B0%2B0/resize/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F500%2Fcomposicion-getty.jpg\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/2af5ab8/2147483647/crop/1499x844%2B0%2B0/resize/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F1500%2Fcomposicion-getty.jpg\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/0497cbb/2147483647/crop/499x281%2B0%2B0/resize/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F500%2Fcomposicion-getty.jpg\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/bb675ed/2147483647/crop/500x280%2B0%2B0/resize/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F500%2Fcomposicion-getty.jpg\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/c1a4787/2147483647/crop/1500x844%2B0%2B0/resize/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F1500%2Fcomposicion-getty.jpg\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/60cfe3d/2147483647/crop/1500x844%2B0%2B0/resize/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F1500%2Fcomposicion-getty.jpg\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/c0ee67f/2147483647/crop/495x281%2B0%2B0/resize/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7f%2F0a%2F0474a93a46bf818cfa1a16fe0a84%2Fresizes%2F500%2Fcomposicion-getty.jpg\",\"width\":30,\"height\":17}}},\"authors\":[{\"fullName\":\"Mariana Rambaldi\",\"image\":{\"type\":\"image\",\"uid\":\"00000153-f1f1-d20a-a97b-fdf934a70000\",\"title\":\"Univision Fallback Image\",\"caption\":null,\"credit\":null,\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/3a/60/ff8a1c114b0cb94b76cf39d1fdaa/newversion.jpg\",\"width\":1200,\"height\":630},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/3d2ab97/2147483647/thumbnail/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fresizes%2F500%2Fnewversion.jpg\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/cf2ec7a/2147483647/thumbnail/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fnewversion.jpg\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/c788a23/2147483647/thumbnail/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fnewversion.jpg\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/57e8adc/2147483647/thumbnail/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fresizes%2F500%2Fnewversion.jpg\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/b36701a/2147483647/thumbnail/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fnewversion.jpg\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/9ee83ed/2147483647/thumbnail/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fnewversion.jpg\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/beb89eb/2147483647/thumbnail/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F3a%2F60%2Fff8a1c114b0cb94b76cf39d1fdaa%2Fresizes%2F500%2Fnewversion.jpg\",\"width\":30,\"height\":17}}},\"uri\":\"/temas/mariana-rambaldi\",\"socialNetworks\":{\"facebookUrl\":null,\"googleUrl\":null,\"instagramUrl\":null,\"pinterestUrl\":null,\"snapchatUrl\":null,\"twitterUrl\":{\"text\":null,\"name\":\"Twitter\",\"url\":\"https://twitter.com/marianarambaldi\",\"target\":\"_blank\"},\"youTubeUrl\":null},\"miniBio\":null,\"link\":null,\"mediaCompany\":null,\"designation\":null}],\"updateDate\":\"2019-04-27T11:04:49-04:00\",\"publishDate\":\"2019-04-27T09:27:21-04:00\",\"expirationDate\":null,\"contentPriority\":\"STANDARD\",\"leadType\":\"image\",\"primaryTag\":{\"name\":\"Política\",\"link\":\"/noticias/politica\"}}," +
            "{\"type\":\"article\",\"uid\":\"0000016a-6066-d0b8-a37a-faee6f0c0002\",\"uri\":\"/deportes/futbol/la-liga/gano-la-liga-caminando-messi-en-modo-messi-dio-al-barcelona-el-titulo-26\",\"vertical\":\"Deportes\",\"parent\":{\"uri\":\"/deportes/futbol/la-liga\",\"name\":\"la liga\",\"title\":\"La Liga\"},\"title\":\"¿Ganó La Liga caminando? Messi en modo Messi dio al Barcelona el título 26\",\"description\":\"Las tres fechas que aún restan serán simples anécdotas en otro año de ensueño del 10 culé engrandeciendo su leyenda.\",\"image\":{\"type\":\"image\",\"uid\":\"0000016a-60ad-d06a-a5fe-79eff0610000\",\"title\":\"83dc9ec233924078a953b099ae5a35ee\",\"caption\":null,\"credit\":null,\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/7a/b7/daf1a06b44718d82a848eec23eef/83dc9ec233924078a953b099ae5a35ee\",\"width\":1920,\"height\":1080},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/45aff50/2147483647/thumbnail/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/bc563df/2147483647/thumbnail/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F1500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/3aa9800/2147483647/thumbnail/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/48d748c/2147483647/thumbnail/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/8475a41/2147483647/thumbnail/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F1500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/37b013b/2147483647/thumbnail/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F1500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/c35564c/2147483647/thumbnail/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F7a%2Fb7%2Fdaf1a06b44718d82a848eec23eef%2Fresizes%2F500%2F83dc9ec233924078a953b099ae5a35ee\",\"width\":30,\"height\":17}}},\"authors\":[],\"updateDate\":\"2019-04-27T17:25:43-04:00\",\"publishDate\":\"2019-04-27T16:36:15-04:00\",\"expirationDate\":null,\"contentPriority\":\"STANDARD\",\"leadType\":\"video\",\"primaryTag\":{\"name\":\"La Liga\",\"link\":\"/deportes/futbol/la-liga\"}}," +
            "{\"type\":\"slideshow\",\"uid\":\"0000016a-5f58-d0b8-a37a-fffcc11e0003\",\"uri\":\"/famosos/este-es-el-camino-que-ferdinando-valencia-y-su-novia-han-recorrido-para-evitar-ser-papas-antes-de-tiempo-fotos\",\"vertical\":\"Entretenimiento\",\"parent\":{\"uri\":\"/famosos\",\"name\":\"famosos\",\"title\":\"Famosos\"},\"title\":\"Este es el camino que Ferdinando Valencia y su novia han recorrido para evitar ser papás antes de tiempo\",\"description\":\"Desde que Brenda Kellerman fuera internada el pasado 16 de abril debido a complicaciones en el embarazo de sus mellizos, el actor ha compartido con sus seguidores las complicaciones y acciones que han tenido que tomar para evitar que sus bebés nazcan antes de lo previsto.\",\"image\":{\"type\":\"image\",\"uid\":\"0000016a-5f96-d08a-abff-7fb6b7940000\",\"title\":\"Thumbnail Ferdinando Valencia\",\"caption\":null,\"credit\":\"Ferdinando Valencia/Instagram\",\"renditions\":{\"original\":{\"href\":\"https://st1.uvnimg.com/9b/5a/00ad56194cbaa0814db5911852fc/thumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":1920,\"height\":1080},\"16x9-med\":{\"href\":\"https://st1.uvnimg.com/dims4/default/8c72e29/2147483647/thumbnail/400x225/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":400,\"height\":225},\"16x9\":{\"href\":\"https://st1.uvnimg.com/dims4/default/78518b6/2147483647/thumbnail/1240x698/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F1500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":1240,\"height\":698},\"16x9-mobile\":{\"href\":\"https://st1.uvnimg.com/dims4/default/39a016b/2147483647/thumbnail/480x270/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":480,\"height\":270},\"16x9-sm\":{\"href\":\"https://st1.uvnimg.com/dims4/default/b519eef/2147483647/thumbnail/246x138/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":246,\"height\":138},\"16x9-tablet\":{\"href\":\"https://st1.uvnimg.com/dims4/default/d66b346/2147483647/thumbnail/1024x576%3E/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F1500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":1024,\"height\":576},\"16x9-extended\":{\"href\":\"https://st1.uvnimg.com/dims4/default/49d17da/2147483647/thumbnail/1440x810/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F1500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":1440,\"height\":810},\"16x9-loading\":{\"href\":\"https://st1.uvnimg.com/dims4/default/99322c0/2147483647/thumbnail/30x17/quality/75/?url=https%3A%2F%2Fuvn-brightspot.s3.amazonaws.com%2F9b%2F5a%2F00ad56194cbaa0814db5911852fc%2Fresizes%2F500%2Fthumbnail-ferdinandovalencia-bendakellerman-270419-a.jpg\",\"width\":30,\"height\":17}}},\"authors\":[],\"updateDate\":\"2019-04-27T13:33:28-04:00\",\"publishDate\":\"2019-04-27T13:33:29-04:00\",\"expirationDate\":null,\"contentPriority\":\"STANDARD\",\"primaryTag\":{\"name\":\"Famosos\",\"link\":\"/famosos\"}}]}]}}";

    @Test
    public void whenUsingAnnotations() throws IOException {
        long start = System.currentTimeMillis();
        WidgetDTO widget = new ObjectMapper().readerFor(WidgetDTO.class)
                .readValue(SOURCE_JSON);
        long end = System.currentTimeMillis();
        System.out.println("DEBUG: Logic UsingAnnotations took " + (end - start) + " MilliSeconds");

        assertNotEquals(true, widget.getContents().isEmpty());
        assertEquals(widget.getContents().size(), 3);
        assertThat(widget.getContents().get(0)).isInstanceOf(ContentByTypeDTO.class);
        for (ContentByTypeDTO content : widget.getContents()) {
            assertNotEquals(true, content.type.isEmpty());
            assertNotEquals(true, content.titles.isEmpty());
            assertNotEquals(false, content.count > 0);
        }
    }

    @Test
    public void whenUsingDeserializerAutoRegistered()
            throws IOException {
        long start = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper();
        WidgetDTO widget = mapper.readValue(SOURCE_JSON, WidgetDTO.class);
        long end = System.currentTimeMillis();
        System.out.println("DEBUG: Logic DeserializerAutoRegistered took " + (end - start) + " MilliSeconds");

        assertNotEquals(true, widget.getContents().isEmpty());
        assertEquals(widget.getContents().size(), 3);
        assertThat(widget.getContents().get(0)).isInstanceOf(ContentByTypeDTO.class);
        for (ContentByTypeDTO content : widget.getContents()) {
            assertNotEquals(true, content.type.isEmpty());
            assertNotEquals(true, content.titles.isEmpty());
            assertNotEquals(false, content.count > 0);
        }
    }

    @Test
    public void whenUsingDeserializerManuallyRegistered()
            throws IOException {
        long start = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(WidgetDTO.class, new WidgetDeserializer());
        mapper.registerModule(module);

        WidgetDTO widget = mapper.readValue(SOURCE_JSON, WidgetDTO.class);
        long end = System.currentTimeMillis();
        System.out.println("DEBUG: Logic DeserializerManually took " + (end - start) + " MilliSeconds");

        assertNotEquals(true, widget.getContents().isEmpty());
        assertEquals(widget.getContents().size(), 3);
        assertThat(widget.getContents().get(0)).isInstanceOf(ContentByTypeDTO.class);
        for (ContentByTypeDTO content : widget.getContents()) {
            assertNotEquals(true, content.type.isEmpty());
            assertNotEquals(true, content.titles.isEmpty());
            assertNotEquals(false, content.count > 0);
        }
    }

    @Test
    public void whenUsingAnnotationsWithEmptyJson_ThenFail() throws IOException {

        assertThrows(
                NullPointerException.class,
                () -> new ObjectMapper().readerFor(WidgetDTO.class)
                        .readValue("{}"),
                "Expected doThing() to throw, but it didn't"
        );
    }

    @Test
    public void whenUsingAnnotationsWithBadJson_ThenFail() throws IOException {

        assertThrows(
                JsonParseException.class,
                () ->new ObjectMapper().readerFor(WidgetDTO.class).readValue("aa"),
                "Expected doThing() to throw, but it didn't"
        );
    }
}
