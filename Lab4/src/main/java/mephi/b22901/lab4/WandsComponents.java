/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Регина
 */
public class WandsComponents {
    
    //древесина от 1 до 24, дальше - сердцевина 

    private static final Map<Integer, String> compMap = new HashMap<>();
    private static final String[] components = {"Остролист", "Тис", "Бузина", "Ива", "Осина", "Дуб", "Кедр", "Лавр", "Сосна", "Можжевельник", "Ясень", "Акация", "Кипарис", "Магнолия", "Ольха", "Рябина", "Вяз", "Вишня", "Береза", "Тополь", "Бук", "Тополь", "Орешник", "Красное дерево",
        "Перо феникса", "Сердечная жила дракона", "Волос хвоста единорога", "Волос вейлы", "Рог рогатого змея", "Волос хвоста фестрала", "Ус тролля", "Волос келпи", "Рог кроленя", "Шерсть вампуса", "Волос русаkки", "Крыло феи", "Перо гиппогрифа", "Волос сфинкса", "Сушеный корень мандрагоры",
        "Зуб василиска", "Коготь грифона", "Чешуя саламандры", "Чешуя дракона", "Жилы крыла летучей мыши", "Перо орла", "Перо ворона", "Перо пегаса", "Волчья шерсть", "Шерсть мантикоры"};

    static {
        for (int i = 0; i < components.length; i++) {
            compMap.put(i + 1, components[i]);
        }
    }

    public static String getComponent(int id) {
        return compMap.get(id);
    }
}
