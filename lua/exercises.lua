-- Write your solutions to the exercises in this file.

function change(amount)
  if amount < 0 then
    error("Amount must be positive")
  end
  local coins, remaining = {}, amount
  for _, coin in ipairs({25, 10, 5}) do
    local count = remaining // coin
    table.insert(coins, count)
    remaining = remaining - count * coin
  end
  table.insert(coins, remaining)
  return table.unpack(coins)
end