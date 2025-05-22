// src/main/java/org/example/entity/TaskData.java
package org.example.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TaskData {
    // Çalışanlara ve atanmamış görevlere özel Set'ler
    private Set<Task> annsTasks;
    private Set<Task> bobsTasks;
    private Set<Task> carolsTasks;
    private Set<Task> unassignedTasks;

    /**
     * TaskData sınıfı constructor'ı. Belirtilen görev setlerini başlatır.
     * Parametre olarak gelen Set'lerin null olmaması için kontrol eder ve yeni HashSet'ler oluşturur.
     * Bu, dışarıdan gelen Set'lerin null olması durumunda NullPointerException'ı önler.
     */
    public TaskData(Set<Task> annsTasks, Set<Task> bobsTasks, Set<Task> carolsTasks, Set<Task> unassignedTasks) {
        this.annsTasks = (annsTasks != null) ? new HashSet<>(annsTasks) : new HashSet<>();
        this.bobsTasks = (bobsTasks != null) ? new HashSet<>(bobsTasks) : new HashSet<>();
        this.carolsTasks = (carolsTasks != null) ? new HashSet<>(carolsTasks) : new HashSet<>();
        this.unassignedTasks = (unassignedTasks != null) ? new HashSet<>(unassignedTasks) : new HashSet<>();
    }

    /**
     * Belirli bir atanan kişiye göre görev setlerini döndürür.
     *
     * @param assigneeName Aranacak kişinin adı ("ann", "bob", "carol", "unassigned") veya "all" hepsi için.
     * @return Belirtilen kişiye ait görevleri veya tüm görevleri içeren bir Set.
     * Geçersiz bir atanan adı verilirse boş bir Set döner.
     */
    public Set<Task> getTasks(String assigneeName) {
        if (assigneeName == null) {
            System.out.println("Assignee name cannot be null. Returning empty set.");
            return Collections.emptySet();
        }

        switch (assigneeName.toLowerCase()) {
            case "ann":
                return Collections.unmodifiableSet(annsTasks);
            case "bob":
                return Collections.unmodifiableSet(bobsTasks);
            case "carol":
                return Collections.unmodifiableSet(carolsTasks);
            case "unassigned": // Atanmamış görevler için
                return Collections.unmodifiableSet(unassignedTasks);
            case "all":
                // Tüm görevleri içeren yeni bir Set oluşturup döndürüyoruz
                Set<Task> allCombinedTasks = new HashSet<>();
                allCombinedTasks.addAll(annsTasks);
                allCombinedTasks.addAll(bobsTasks);
                allCombinedTasks.addAll(carolsTasks);
                allCombinedTasks.addAll(unassignedTasks);
                return Collections.unmodifiableSet(allCombinedTasks);
            default:
                System.out.println("Invalid assignee name: " + assigneeName + ". Returning empty set.");
                return Collections.emptySet();
        }
    }

    // --- Küme İşlemleri Yardımcı Metotları ---

    /**
     * Bir Set listesi alır ve aldığı tüm Set değerlerini birleştirip (union) döner.
     * @param sets Birleştirilecek Set'lerin listesi.
     * @return Tüm Set'lerin birleşimi olan yeni bir Set.
     */
    public Set<Task> getUnion(List<Set<Task>> sets) {
        Set<Task> unionSet = new HashSet<>();
        if (sets != null) {
            for (Set<Task> set : sets) {
                if (set != null) {
                    unionSet.addAll(set);
                }
            }
        }
        return Collections.unmodifiableSet(unionSet);
    }
    public Set<Task> getUnion(Set<Task> set1, Set<Task> set2) {
        Set<Task> unionSet = new HashSet<>();
        if (set1 != null) {
            unionSet.addAll(set1);
        }
        if (set2 != null) {
            unionSet.addAll(set2);
        }
        return Collections.unmodifiableSet(unionSet);
    }

    /**
     * İki Set alır ve aralarındaki kesişim kümesini (intersection) bulup döner.
     * @param set1 İlk Set.
     * @param set2 İkinci Set.
     * @return İki Set'in kesişimi olan yeni bir Set.
     */
    public Set<Task> getIntersection(Set<Task> set1, Set<Task> set2) {
        if (set1 == null || set2 == null) {
            return Collections.emptySet(); // Null input için boş küme döndür
        }
        Set<Task> intersectionSet = new HashSet<>(set1); // set1'in bir kopyasını al
        intersectionSet.retainAll(set2); // Bu kopya üzerinde kesişim işlemini yap
        return Collections.unmodifiableSet(intersectionSet); // Dışarıya değiştirilemez Set döndür
    }

    /**
     * İki Set alır ve ikinci Set içerisindeki verileri ilk Set içerisindeki verilerden çıkarır (difference).
     * Yani, set1'de olup set2'de olmayan elemanları içeren bir Set döndürür.
     * @param set1 Çıkarma işleminin yapılacağı ana Set.
     * @param set2 Çıkarılacak elemanları içeren Set.
     * @return set1'den set2'nin elemanları çıkarıldıktan sonra kalan yeni bir Set.
     */
    public Set<Task> getDifferences(Set<Task> set1, Set<Task> set2) {
        if (set1 == null || set2 == null) {
            return Collections.emptySet(); // Null input için boş küme döndür
        }
        Set<Task> differenceSet = new HashSet<>(set1); // set1'in bir kopyasını al
        differenceSet.removeAll(set2); // Bu kopya üzerinde fark işlemini yap
        return Collections.unmodifiableSet(differenceSet); // Dışarıya değiştirilemez Set döndür
    }

    // --- Müdürünüzün Sorularına Cevap Veren Metotlar ---

    /**
     * Tüm çalışanların (Ann, Bob, Carol) üzerindeki tüm görevleri birleştirilmiş bir Set olarak döndürür.
     * Atanmamış görevler bu listeye dahil değildir.
     * @return Tüm atanmış görevleri içeren bir Set.
     */
    public Set<Task> getAllAssignedTasks() {
        List<Set<Task>> assignedSets = new ArrayList<>();
        assignedSets.add(getTasks("ann"));
        assignedSets.add(getTasks("bob"));
        assignedSets.add(getTasks("carol"));
        return getUnion(assignedSets);
    }

    /**
     * Her bir çalışanın (Ann, Bob, Carol) üzerindeki görevleri ayrı ayrı listeler.
     * @return Atanan kişiye göre görevleri içeren formatlanmış bir String.
     */
    public String getTasksForEachAssignee() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Her Çalışanın Üzerindeki Görevler ---");
        sb.append("\nAnn'in Görevleri:\n");
        Set<Task> annTasksList = getTasks("ann");
        if (annTasksList.isEmpty()) sb.append("  (Hiç yok)\n");
        annTasksList.forEach(task -> sb.append("  - ").append(task.toString()).append("\n"));

        sb.append("\nBob'un Görevleri:\n");
        Set<Task> bobTasksList = getTasks("bob");
        if (bobTasksList.isEmpty()) sb.append("  (Hiç yok)\n");
        bobTasksList.forEach(task -> sb.append("  - ").append(task.toString()).append("\n"));

        sb.append("\nCarol'ın Görevleri:\n");
        Set<Task> carolTasksList = getTasks("carol");
        if (carolTasksList.isEmpty()) sb.append("  (Hiç yok)\n");
        carolTasksList.forEach(task -> sb.append("  - ").append(task.toString()).append("\n"));

        sb.append("--------------------------------------\n");
        return sb.toString();
    }

    /**
     * Herhangi bir çalışana atanması yapılmamış olan görevleri döndürür.
     * @return Atanmamış görevleri içeren bir Set.
     */
    public Set<Task> getUnassignedTasks() {
        return getTasks("unassigned"); // Zaten var olan getTasks("unassigned") metodunu kullanıyoruz
    }

    /**
     * Birden fazla çalışana atanmış (aynı project ve description'a sahip) görevleri bulur.
     * Task'ın benzersizliği project ve description ile belirlendiğinden,
     * aynı task'ın farklı atanan kişilerde görünmesi, birden fazla kişiye atandığı anlamına gelir.
     *
     * @return Birden fazla çalışana atanmış taskları içeren bir Set.
     */
    public Set<Task> getTasksAssignedToMultipleAssignees() {
        Set<Task> commonTasks = new HashSet<>();

        // Ann ve Bob arasındaki ortak görevler
        commonTasks.addAll(getIntersection(getTasks("ann"), getTasks("bob")));
        // Ann ve Carol arasındaki ortak görevler
        commonTasks.addAll(getIntersection(getTasks("ann"), getTasks("carol")));
        // Bob ve Carol arasındaki ortak görevler
        commonTasks.addAll(getIntersection(getTasks("bob"), getTasks("carol")));

        // Tüm üçünün ortak görevleri de yukarıdaki eklemelerde zaten dahil edilmiş olur.
        // Bu metod Task objelerinin project ve description ile eşit olduğunu varsayar.
        // Eğer aynı project+description'a sahip task'lar birden fazla set'te yer alıyorsa,
        // bu, o task'ın birden fazla kişiye atandığı anlamına gelir.

        if (commonTasks.isEmpty()) {
            System.out.println("Not: Proje ve açıklamasına göre birden fazla çalışana atanmış görev bulunamadı.");
        }
        return Collections.unmodifiableSet(commonTasks);
    }

    // Tüm görevleri yazdırmak için yardımcı metot
    public void printAllTasks() {
        Set<Task> allTasks = getTasks("all"); // Tüm görevleri almak için getTasks metodunu kullan
        if (allTasks.isEmpty()) {
            System.out.println("Hiç görev mevcut değil.");
            return;
        }
        System.out.println("\n--- Tüm Görevler ---");
        allTasks.forEach(System.out::println);
        System.out.println("--------------------");
    }
}