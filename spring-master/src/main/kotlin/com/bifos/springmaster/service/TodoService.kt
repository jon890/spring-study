package com.bifos.springmaster.service

import com.bifos.springmaster.domain.Todo
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.persistence.EntityNotFoundException

@Service
class TodoService {

    companion object {
        val todos = mutableListOf<Todo>()
        var todoCount = 3

        init {
            todos.add(Todo(1, "잭", "스프링 MVC를 배우자", LocalDate.now(), false))
            todos.add(Todo(2, "잭", "스트럿츠를 배우자", LocalDate.now(), false))
            todos.add(Todo(3, "질", "하이버네이트를 배우자", LocalDate.now(), false))
        }
    }

    fun retrieveTodos(user: String) = todos.filter { it.user == user }

    fun addTodo(name: String, desc: String, targetDate: LocalDate, isDone: Boolean): Todo {
        todoCount++
        val newTodo = Todo(todoCount, name, desc, targetDate, isDone)
        todos.add(newTodo)
        return newTodo
    }

    fun retrieveTodo(id: Int) = todos.find { it.id == id } ?: throw EntityNotFoundException("할 일이 존재하지 않습니다")


    fun update(todo: Todo): Todo {
        retrieveTodo(todo.id).let {
            it.update(todo)
            return it
        }
    }

    fun deleteById(id: Int): Todo {
        retrieveTodo(id).let {
            todos.remove(it)
            return it
        }
    }
}