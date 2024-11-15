#include "string_stack.h"
#include <stdlib.h>
#include <string.h>

#define INITIAL_CAPACITY 16

struct _Stack {
    char** elements;
    int top;
    int capacity;
};

stack_response create() {
    // TODO - long, 11 lines
    // initialize stack and set its top to 0, and return it
    stack s = malloc(sizeof(struct _Stack));
    if (s == NULL) {
        return (stack_response){.code = out_of_memory, .stack = NULL};
    }
    s->capacity = INITIAL_CAPACITY;
    s->elements = malloc(INITIAL_CAPACITY * sizeof(char*));
    // Check for out of memory
    //YOU DO THIS 
    if (s->elements == NULL) {
        free(s);
        return (stack_response){.code = out_of_memory, .stack = NULL};
    }
    //
    //
    s->top = 0;
    return (stack_response){.code = success, .stack = s};
}

int size(const stack s) {
    // TODO
    return s->top;
}

bool is_empty(const stack s) {
    // TODO - size is 0, top is 0
    return size(s) == 0;
}

bool is_full(const stack s) { // If at MAX_CAPACITY
    // TODO - return top is at MAX_CAPACITY
    return s->top >= MAX_CAPACITY;
}              

response_code push(stack s, char* item) {// Stores copy of string inside stack
    // TODO
    if (is_full(s)) {
        return stack_full;
    }
    if (strlen(item) >= MAX_ELEMENT_BYTE_SIZE) {
        return stack_element_too_large;
    }
    if (s->top == s->capacity) {
        // we need to resize, we need to make it twice as big
        int new_capacity = s->capacity * 2;
        if (new_capacity > MAX_CAPACITY) {
            new_capacity = MAX_CAPACITY;
        }
        char** new_elements = realloc(s->elements, new_capacity * sizeof(char*));
        if (new_elements == NULL) {
            return out_of_memory;
        }
        s->elements = new_elements;
        s->capacity = new_capacity;
    }

        //FOR YOU: MAKE SURE THE STRING BEING PASSED IN IS NOT TOO BIG
        //return stack_element_too_large if so -> did so above
    s->elements[s->top] = strdup(item);
    if (s->elements[s->top] == NULL) {
            return out_of_memory;
    }
    s->top++;
    return success;

        // s->elements[s->top++] = strdup(item);
        // return success;
    }




string_response pop(stack s) {
    // TODO
    // First things to check: is the stack empty?
    if (is_empty(s)) {
        return (string_response){.code = stack_empty, .string = NULL};
    }
    char* popped = s->elements[--s->top];

    
    if (s->top < s->capacity / 4 && s->capacity > INITIAL_CAPACITY) {
        int new_capacity = s->capacity / 2;
        if (new_capacity < INITIAL_CAPACITY) {
            new_capacity = INITIAL_CAPACITY;
        }
        char** new_elements = realloc(s->elements, new_capacity * sizeof(char*));
        if (new_elements != NULL) {  // Realloc success
            s->elements = new_elements;
            s->capacity = new_capacity;
        }
    }
    // //if the capacity went below 1/4, we should shrink it
    // if new_capacity = s->capacity / 2;
    //     if (new_capacity < 1) {
    //         new_capacity = 1;
    //     }
    //     char** new_elements = realloc(s->elements, new_capacity * sizeof(char*));
    //     if (new_elements == NULL) {
    //         return out_of_memory;
    //     }
    //     s->elements = new_elements;
    //     s->capacity = new_capacity;
    //
    //
    //
    return (string_response){.code = success, .string = popped};
}             
// Will include a copy of the string
// from the stack, so the caller is
// responsible for freeing it

void destroy(stack* s) { // frees *all* the memory
    if (s == NULL || *s == NULL) return;
    
    // Free each string
    for (int i = 0; i < (*s)->top; i++) {
        free((*s)->elements[i]);
    }
    // Free elements array and the stack itself
    free((*s)->elements);
    free(*s);
    *s = NULL;  // Set pointer to NULL after freeing
}