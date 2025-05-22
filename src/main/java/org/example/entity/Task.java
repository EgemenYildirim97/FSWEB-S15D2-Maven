// src/main/java/org/example/entity/Task.java
package org.example.entity;

import java.util.Objects;

public class Task {
    private String project;
    private String description;
    private String assignee; // Görevin atandığı kişi
    private Priority priority;
    private Status status;

    public Task(String project, String description, String assignee, Priority priority, Status status) {
        this.project = project;
        this.description = description;
        this.assignee = assignee;
        this.priority = priority;
        this.status = status;
    }

    // --- Getter Metotları ---
    public String getProject() {
        return project;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignee() {
        return assignee;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    // --- Setter Metotları (İsteğe bağlı, ancak esneklik için faydalıdır) ---
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * İki Task objesini eşit kabul etmek için sadece 'project' ve 'description' değerlerini karşılaştırır.
     * Bu, bu iki alanın Task'ın benzersizliğini belirlediği anlamına gelir.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Aynı referans ise eşit
        if (o == null || getClass() != o.getClass()) return false; // Null ise veya farklı sınıftan ise eşit değil
        Task task = (Task) o; // Obeyi Task tipine dönüştür
        // 'project' ve 'description' alanlarının eşitliğini kontrol et
        return Objects.equals(project, task.project) &&
                Objects.equals(description, task.description);
    }

    /**
     * 'equals' metodu override edildiğinde 'hashCode' metodu da override edilmelidir.
     * 'equals' metodunda kullanılan aynı alanlar ('project' ve 'description') burada da kullanılır.
     * Bu, HashMap ve HashSet gibi koleksiyonların doğru çalışması için kritik öneme sahiptir.
     */
    @Override
    public int hashCode() {
        return Objects.hash(project, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "project='" + project + '\'' +
                ", description='" + description + '\'' +
                ", assignee='" + (assignee != null ? assignee : "Unassigned") + '\'' + // null ise "Unassigned" göster
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }
}