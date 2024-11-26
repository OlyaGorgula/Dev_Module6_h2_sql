select name, salary
from worker
where salary IN (select MAX(salary) from worker);
