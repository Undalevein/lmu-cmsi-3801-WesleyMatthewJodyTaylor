#include "string_stack.h"
#include <stdlib.h>
#include <string.h>

// Complete your string stack implementation in this file.

struct _Stack {
    char** items;       // Array of string pointers
    int capacity;       // Current capacity of the stack
    int size;           // Current number of elements in the stack
};

stack_response create() {
    stack s = (stack)malloc(sizeof(struct _Stack));
    if (s == NULL) {
        return (stack_response){ .code = out_of_memory, .stack = NULL };
    }

    s->capacity = 16; // Initial capacity
    s->size = 0;
    s->items = (char**)malloc(s->capacity * sizeof(char*));
    if (s->items == NULL) {
        free(s);
        return (stack_response){ .code = out_of_memory, .stack = NULL };
    }

    return (stack_response){ .code = success, .stack = s };
}

bool is_empty(const stack s) {
    return s->size == 0;
}

bool is_full(const stack s) {
    return s->size >= MAX_CAPACITY; // Check against MAX_CAPACITY
}

int size(const stack s) {
    return s->size;
}

response_code push(stack s, char* item) {
    // Check if the string length exceeds the maximum allowed length
    if (strlen(item) > MAX_ELEMENT_BYTE_SIZE) {
        return stack_element_too_large; // Return the error code for long strings
    }

    // Expand capacity if full
    if (s->size >= s->capacity) {
        // Check if we have reached the maximum capacity limit
        if (s->capacity >= MAX_CAPACITY) {
            return stack_full; // Return an error code if the stack is full
        }

        // Double the capacity
        int new_capacity = s->capacity * 2;
        if (new_capacity > MAX_CAPACITY) {
            new_capacity = MAX_CAPACITY; // Ensure it does not exceed MAX_CAPACITY
        }

        // Allocate a new array with the new capacity
        char** new_items = (char**)realloc(s->items, new_capacity * sizeof(char*));
        if (new_items == NULL) {
            return out_of_memory; // Return an error code if memory allocation fails
        }
        s->items = new_items;
        s->capacity = new_capacity; // Update the capacity
    }

    // Allocate memory for the new string and copy the item into it
    char* new_item = (char*)malloc((strlen(item) + 1) * sizeof(char));
    if (new_item == NULL) {
        return out_of_memory; // Return an error code if memory allocation fails
    }
    strcpy(new_item, item);

    // Add the new item to the stack
    s->items[s->size] = new_item;
    s->size++; // Increase the size of the stack

    return success; // Return success code
}

string_response pop(stack s) {
    string_response res;
    if (is_empty(s)) {
        res.code = stack_empty; // Stack is empty
        res.string = NULL;
        return res;
    }

    // Get the last string item from the stack
    char* popped_item = s->items[s->size - 1];
    s->size--; // Decrease the size of the stack

    // Create a new string response for the popped item
    res.code = success;
    res.string = popped_item; // The caller is responsible for freeing this

    return res;
}

void destroy(stack* s) {
    if (s == NULL || *s == NULL) return;

    // Free all the strings in the stack
    for (int i = 0; i < (*s)->size; i++) {
        free((*s)->items[i]);
    }
    free((*s)->items); // Free the array of pointers
    free(*s);          // Free the stack structure
    *s = NULL;        // Nullify the pointer
}
