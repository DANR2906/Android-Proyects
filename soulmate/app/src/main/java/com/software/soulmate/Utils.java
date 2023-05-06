package com.software.soulmate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    private static final String[] NOUNS = {
            "heart", "soul", "sunshine", "angel", "treasure", "dream", "star", "princess", "queen", "diamond", "darling"
            , "sweetheart", "sweetheart", "dearest", "light"
    };

    private static final String[] ADJECTIVES = {
            "Loving", "Adorable", "Sweet", "Beautiful", "Precious", "Magical", "Captivating", "Enchanted", "Blissful", "Irresistible", "Tender",
            "Passionate", "Affectionate", "Charming", "Devoted", "Caring", "Romantic", "Heartfelt", "Admired", "Sincere", "Gentle", "Endearing",
            "Enchanting", "Blissful", "Delightful", "Serene", "Graceful", "Alluring", "Amiable", "Tender-hearted"
    };

    private static final String[] VERBS = {
            "cherish", "adore", "love", "treasure", "worship", "appreciate", "desire", "embrace", "admire", "long for", "Crave", "Yearn", "Cuddle",
            "Pamper", "Romance", "Cherish", "Adore", "Love", "Treasure", "Worship", "Appreciate", "Desire", "Embrace", "Admire", "Long for", "Enchant",
            "Swoon", "Delight", "Savor", "Enrapture", "Serenade", "Caress", "Whisper", "Honor", "Yearn for", "Crave", "Hug", "Kiss", "Celebrate",
            "Seduce", "Entice"
    };

    private static final String[] ADVERBS = {
            "endlessly", "passionately", "tenderly", "deeply", "completely", "devotedly", "intimately", "forever", "unconditionally", "wholeheartedly",
            "Eternally", "Fondly", "Gently", "Lovingly", "Passionately", "Romantically", "Sincerely", "Affectionately", "Closely", "Endearingly",
            "Enthusiastically", "Happily", "Soulfully", "Tirelessly", "Warmly", "Yearningly", "Blissfully", "Devotedly", "Intimately", "Deeply",
            "Completely", "Adoringly", "Tenderly", "Wholeheartedly", "Unreservedly", "Zealously", "Tenderly", "Fervently", "Fiercely", "Generously"
    };

    public static List<String> lovePhrases = Arrays.asList(
            "Eres mi razón de ser.",
            "Tus abrazos son mi refugio.",
            "Amar es encontrar en tu felicidad la mia.",
            "Eres el sueño que nunca quiero despertar.",
            "Tú y yo, juntos por siempre.",
            "Eres la persona con la que quiero envejecer.",
            "Nuestro amor no conoce barreras.",
            "Cada día te quiero más que ayer y menos que mañana.",
            "Eres la mejor parte de mi vida soulmate.",
            "Tu sonrisa ilumina mi mundo.",
            "Eres mi complemento perfecto.",
            "Contigo todo es mejor.",
            "Eres mi sueño hecho realidad.",
            "Amarte es darme cuenta de que me haces ser la mejor versión de mi mismo.",
            "Eres mi sol en días nublados.",
            "No necesito un cuento de hadas, solo quiero estar contigo.",
            "Eres mi todo.",
            "El amor no se encuentra, se construye.",
            "Tú eres mi para siempre.",
            "Amar es encontrar en el otro la propia realización.",
            "No sé qué haría sin ti en mi vida.",
            "Eres mi refugio en momentos difíciles.",
            "Eres el latido de mi corazón.",
            "Mi amor por ti es infinito.",
            "Eres mi mayor inspiración.",
            "No puedo imaginar mi vida sin ti.",
            "Eres mi razón de sonreír.",
            "Tú haces que cada día sea especial.",
            "Eres mi refugio seguro en medio de la tormenta.",
            "Cada momento a tu lado es un regalo.",
            "Eres el sueño que nunca quiero despertar.",
            "Amar es encontrar en ti mi lugar en el mundo.",
            "Tus ojos son el espejo de mi alma.",
            "Mi felicidad tiene tu nombre.",
            "Eres el complemento perfecto en mi vida.",
            "Amar es descubrir la magia en tus abrazos.",
            "Eres el sol que ilumina mi existencia.",
            "Tu amor me hace sentir completo.",
            "Eres mi fortaleza y mi apoyo incondicional.",
            "Tú y yo, juntos somos invencibles.",
            "Amar es confiar plenamente en el otro.",
            "Eres mi sueño hecho realidad.",
            "Tus besos son el mejor remedio para todo.",
            "Amar es quererte incluso en tus imperfecciones.",
            "Eres la melodía que alegra mi vida.",
            "Tú haces que cada día sea especial.",
            "Amar es ver el mundo a través de tus ojos.",
            "Eres mi razón de existir.",
            "Tus abrazos me dan vida.",
            "En tu sonrisa encuentro la felicidad.",
            "Eres mi confidente y compañera de vida.",
            "Amar es compartir sueños y metas contigo.",
            "Eres mi paz en medio del caos.",
            "No hay distancia que pueda separar nuestro amor.",
            "Cada día te amo más y más.",
            "Eres el regalo más hermoso que la vida me ha dado.",
            "Eres mi inspiración para ser mejor cada día.",
            "Tu amor me llena de alegría y gratitud.",
            "Amar es encontrarte en cada latido de mi corazón.",
            "Eres el reflejo de todas mis ilusiones y anhelos.",
            "Nuestro amor es un viaje sin fin, lleno de aventuras.",
            "Eres el faro que guía mi camino.",
            "Amar es entregarse sin miedo ni reservas.",
            "Eres mi refugio en los días grises.",
            "Tú y yo, unidos por siempre en un amor eterno.",
            "En tus ojos encuentro el universo entero.",
            "Eres la melodía que embellece mi existencia.",
            "Juntos creamos momentos inolvidables.",
            "Eres mi razón para sonreír cada día.",
            "Nuestro amor es un regalo que valoro infinitamente.",
            "Amar es sentir mariposas en el estómago cada vez que te veo.",
            "Eres el sueño que nunca supe que tenía.",
            "Amar es entregarse sin reservas ni condiciones.",
            "Eres la pieza que faltaba en mi rompecabezas.",
            "Tu amor es el combustible que impulsa mi felicidad.",
            "Contigo descubrí lo que significa amar de verdad.",
            "Eres el faro que ilumina mi camino en los momentos oscuros.",
            "Amar es aprender a amar tus imperfecciones y abrazar tu esencia.",
            "Eres mi confidente y mi mejor amiga.",
            "El amor que siento por ti es un fuego que nunca se apaga.",
            "Eres mi refugio en medio del caos del mundo.",
            "Amar es construir un futuro juntos, paso a paso.",
            "Eres el sueño convertido en realidad que nunca quiero despertar.",
            "Amar es comprendernos mutuamente sin necesidad de palabras.",
            "Eres el sol que ilumina mis días y la luna que ilumina mis noches.",
            "Amar es amarte incluso cuando estemos en silencio, solo con mirarnos.",
            "Eres mi inspiración para alcanzar todas mis metas y sueños.",
            "En tus brazos encuentro mi hogar.",
            "Tu amor es el mejor regalo que he recibido.",
            "Eres mi razón para despertar con una sonrisa cada mañana.",
            "Amar es perderse en la inmensidad de tus ojos.",
            "Eres el sueño que nunca quise despertar de nuevo.",
            "Mi corazón late al ritmo de tu nombre.",
            "Amar es escribir una historia juntos, página tras página.",
            "Eres la melodía que siempre resuena en mis pensamientos.",
            "Encontré el amor verdadero contigo a mi lado.",
            "Eres el destello de luz en mis días más oscuros.",
            "Amar es descubrir la magia en cada pequeño momento a tu lado.",
            "Eres mi mayor inspiración para ser la mejor versión de mí mismo.",
            "Amar es caminar juntos, sin importar las adversidades del camino.",
            "Eres mi paz en medio del caos y la tormenta.",
            "Amar es convertir lo ordinario en extraordinario cuando estamos juntos.",
            "Eres el tesoro más valioso que guardo en mi corazón.",
            "En cada latido de mi corazón, solo escucho tu nombre.",
            "Amar es construir un castillo de sueños y promesas infinitas.",
            "Eres el motivo por el que creo en el amor a primera vista.",
            "Amar es mirarte y darme cuenta de que no necesito nada más.",
            "Eres el lienzo en blanco donde pinto mi felicidad.",
            "Amar es saber que mi lugar favorito está a tu lado, sin importar dónde estemos.",
            "Eres el suspiro que escapa de mis labios cada vez que te veo.",
            "Amar es encontrar en ti todo lo que necesito y deseo.",
            "Eres el refugio que calma todas mis tormentas internas.",
            "Amar es bailar al ritmo de nuestros corazones entrelazados.",
            "Eres mi inspiración para creer en los cuentos de hadas y los finales felices.",
            "Amar es descubrir el significado de la eternidad en cada instante contigo.",
            "Eres el abrazo que siempre espero al final del día.",
            "Amar es tener la certeza de que no hay distancias que puedan separarnos.",
            "Eres mi compañera de aventuras y cómplice en todas mis travesuras.",
            "Amar es encontrar en tus labios el sabor del paraíso.",
            "Eres la respuesta a todas mis preguntas y el motivo de mis sonrisas.",
            "Amar es perderme en tu mirada y encontrarme en tu corazón.",
            "Eres el sueño que nunca quise despertar porque a tu lado soy feliz.",
            "Amar es el hilo invisible que une nuestras almas para siempre.",
            "Amar es sentir mariposas en el estómago cada vez que te acercas.",
            "Eres la razón por la que creo en los finales felices.",
            "En tus abrazos encuentro el refugio perfecto del mundo.",
            "Amar es bailar al compás de nuestros corazones entrelazados.",
            "Eres el sueño hecho realidad que nunca supe que tenía.",
            "Amar es ver el universo en tus ojos y descubrir que soy infinito a tu lado.",
            "Eres la melodía que embriaga mi alma y llena de alegría mi ser.",
            "Amar es entregarse sin reservas, confiando en que seremos eternos.",
            "Eres la brújula que guía mis pasos hacia un amor verdadero.",
            "Amar es descubrir que no hay distancia ni tiempo que pueda separarnos.",
            "Eres la clave que desbloquea la puerta de la felicidad en mi vida.",
            "Amar es compartir sueños, metas y aventuras a tu lado.",
            "Eres el sol que ilumina mis días y la luna que guía mis noches.",
            "Amar es encontrar en tu sonrisa la fuerza para superar cualquier obstáculo.",
            "Eres el eco de mis pensamientos más dulces y el susurro de mis anhelos.",
            "Amar es crear un mundo único donde solo existamos tú y yo.",
            "Eres el abrazo que necesito al despertar y al caer la noche.",
            "Amar es descubrir que, sin ti, mi corazón no sería más que un rompecabezas incompleto.",
            "Eres el cimiento sólido sobre el que construyo mis sueños y esperanzas.",
            "Amar es encontrar en tu piel el lienzo donde dibujo mis mejores recuerdos.",
            "Eres el faro que ilumina mi camino en los momentos de oscuridad.",
            "Amar es compartir cada latido y respirar al unísono en un abrazo apasionado.",
            "Eres la chispa que enciende la llama eterna de mi corazón.",
            "Amar es descubrir en tu mirada el horizonte de infinitas posibilidades.",
            "Eres el oasis en el desierto de mi vida, el agua que sacia mi sed de amor.",
            "Amar es entregarse sin miedo a perderse, confiando en que siempre nos encontraremos.",
            "Eres la respuesta a todas mis preguntas y la solución a todos mis problemas.",
            "Amar es escribir nuestra historia de amor en las estrellas y hacerla eterna.",
            "Eres el sueño que nunca quiero despertar, la realidad que supera cualquier fantasía.",
            "Amar es descubrir en cada beso el lenguaje silencioso del corazón.",
            "Eres el destino al que siempre regresaré, sin importar cuántos caminos tome.",
            "Amar es encontrar en tu voz la melodía que calma mi alma inquieta.",
            "Eres el reflejo de mis sueños y el motivo de mis mejores sonrisas.",
            "Amar es entregarse sin medida, confiando en que el amor siempre prevalecerá.",
            "Eres el hogar al que siempre perteneceré, sin importar dónde estemos.",
            "Amar es descubrir en tus caricias la magia que transforma la piel en poesía.",
            "Eres la pieza clave que da sentido a todas las piezas dispersas de mi vida.",
            "Amar es escribir en cada latido tu nombre, haciendo eco en cada rincón de mi ser.",
            "Eres el tesoro más valioso que guardo en lo más profundo de mi corazón.",
            "Amar es construir un castillo de sueños juntos, donde reinemos para siempre.",
            "Eres la luz que disipa la oscuridad y el aliento que renueva mi existencia.",
            "Amar es encontrar en tus abrazos la calma que ahuyenta todos mis temores.",
            "Eres el motivo por el que cada día despierto con esperanza y alegría en el corazón.",
            "Amar es bailar al compás de nuestros latidos, creando una melodía eterna.",
            "Eres el sol radiante que ilumina mi mundo y el norte que guía mis pasos.",
            "Amar es reconocer que, juntos, somos más fuertes y podemos enfrentar cualquier desafío.",
            "Eres el sueño que se hizo realidad y superó todas mis expectativas.",
            "Amar es dibujar sonrisas en los rostros tristes y llenar de color los días grises.",
            "Eres el puente que une dos almas, creando un amor indestructible.",
            "Amar es descubrir en tus ojos el universo entero y perderme en su inmensidad.",
            "Eres el motivo por el que creer en los milagros y en la magia del amor.",
            "Amar es encontrar en tu ser la respuesta a todas las preguntas que el corazón plantea.",
            "Eres el regalo más preciado que la vida me ha otorgado, y siempre te valoraré.",
            "Amar es navegar en el océano de tus pensamientos y descubrir la belleza de tu mente.",
            "Eres la llave que abrió las puertas de mi corazón y desató una tormenta de emociones.",
            "Amar es reconocer que cada instante a tu lado es un tesoro que atesoro en mi memoria.",
            "En cada latido de mi corazón, solo escucho tu voz.",
            "Eres el sueño que siempre quise vivir.",
            "Amar es descubrir en tus besos el sabor del infinito.",
            "Eres el amanecer que ilumina mis días y la estrella que guía mis noches.",
            "Eres la melodía que danza en mis pensamientos más dulces.",
            "En tu sonrisa encuentro la razón para creer en los milagros.",
            "Amarte es descubrir en cada caricia el significado del paraíso.",
            "Eres el refugio donde encuentro paz y serenidad.",
            "Encontré en ti la felicidad que siempre buscaba."
    );

    public static String getLovePhrases() {
        Random random = new Random();
        return lovePhrases.get(random.nextInt(lovePhrases.size()));
    }

    public static String generateLovingPhrase() {
        Random random = new Random();

        String noun = NOUNS[random.nextInt(NOUNS.length)];
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String verb = VERBS[random.nextInt(VERBS.length)];
        String adverb = ADVERBS[random.nextInt(ADVERBS.length)];

        return "You are my " + adjective + " " + noun + ". I " + verb + " you " + adverb + ".";
    }

    public static String getRandomPhrase() {
        Random random = new Random();

        int x = random.nextInt(10);

        if (x <= 7)
            return getLovePhrases();
        else
            return generateLovingPhrase();
    }


}
