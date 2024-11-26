select *
from (select 'YOUNGEST' as type, name, birthday
      from worker
      where birthday IN (select MAX(birthday) from worker)
     UNION
      select 'ELDEST' as type, name, birthday
      from worker
      where birthday IN (select MIN(birthday) from worker)
    )
ORDER BY type desc
;

