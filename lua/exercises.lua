function change(amount)
  if math.type(amount) ~= "integer" then
    error("Amount must be an integer")
  end
  if amount < 0 then
    error("Amount cannot be negative")
  end
  local counts, remaining = {}, amount
  for _, denomination in ipairs({25, 10, 5, 1}) do
    counts[denomination] = remaining // denomination
    remaining = remaining % denomination
  end
  return counts
end

-- Write your first then lower case function here
function first_then_lower_case(table, predicate)
    for _, item in pairs(table) do
      if predicate(item) then
        return item:lower()
      end
    end
    return nil
  end

-- Write your powers generator here
function powers_generator(base, limit)
    return coroutine.create(function ()
        local power, result = 0, base
        while base ^ power <= limit do 
            result, power = base ^ power, power + 1
            coroutine.yield(result)
        end
    end)
  end
  

-- Write your say function here
function say(word)
    local createdString = {}
    function nextWord(newWord)
        if newWord == "" then
            table.insert(createdString, "")
        elseif newWord then
            table.insert(createdString, newWord)
        else
            return table.concat(createdString, " ")
        end

        return nextWord
    end

    return nextWord(word)
end

-- Write your line count function here
function meaningful_line_count(filename) 
    local file = io.open(filename)
    if not file then
      error("No such file")
    end
  
    local count = 0
  
    for line in file:lines() do
      local trimmed_line = line:match("^%s*(.-)%s*$") -- removes all whitespace
        if trimmed_line ~= "" and not trimmed_line:match("^%s*#") then
          count = count + 1
        end
    end
  
    file:close()
    return count
  end

-- Write your Quaternion table here
Quaternion = {}
Quaternion.__index = Quaternion

function Quaternion.new(a, b, c, d)
    local self = setmetatable({}, Quaternion)
    self.a = a or 0.0
    self.b = b or 0.0
    self.c = c or 0.0
    self.d = d or 0.0
    return self
end

function Quaternion:coefficients()
    return {self.a, self.b, self.c, self.d}
end

function Quaternion:conjugate()
    return Quaternion.new(self.a, -self.b, -self.c, -self.d)
end

function Quaternion:__add(other)
    return Quaternion.new(
        self.a + other.a,
        self.b + other.b,
        self.c + other.c,
        self.d + other.d
    )
end

function Quaternion:__mul(other)
    local a1, b1, c1, d1 = self.a, self.b, self.c, self.d
    local a2, b2, c2, d2 = other.a, other.b, other.c, other.d
    return Quaternion.new(
        a1 * a2 - b1 * b2 - c1 * c2 - d1 * d2,
        a1 * b2 + b1 * a2 + c1 * d2 - d1 * c2,
        a1 * c2 - b1 * d2 + c1 * a2 + d1 * b2,
        a1 * d2 + b1 * c2 - c1 * b2 + d1 * a2
    )
end

function Quaternion:__eq(other)
    return self.a == other.a and self.b == other.b and self.c == other.c and self.d == other.d
end

function Quaternion:__tostring()
    local parts = {}
    
    if self.a ~= 0 then
        table.insert(parts, tostring(self.a))
    end

    local function add_part(coeff, symbol)
        if coeff ~= 0 then
            local part
            if math.abs(coeff) == 1 then
                part = (coeff < 0 and '-' or '') .. symbol
            else
                part = tostring(coeff) .. symbol
            end
            table.insert(parts, part)
        end
    end
    
    add_part(self.b, 'i')
    add_part(self.c, 'j')
    add_part(self.d, 'k')
    
    if #parts == 0 then
        return '0'
    end
    
    local result = table.remove(parts, 1)
    for _, part in ipairs(parts) do
        if part:sub(1, 1) == '-' then
            result = result .. part
        else
            result = result .. '+' .. part
        end
    end
    return result
end
