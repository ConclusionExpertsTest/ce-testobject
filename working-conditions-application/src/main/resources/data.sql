INSERT INTO salarygroup (id, salarygroupcode, minamount, maxamount) VALUES
  (0, 'A', 2000.00, 2500.00),
  (1, 'B', 2500.00, 3000.00),
  (2, 'C', 3000.00, 3500.00),
  (3, 'D', 3500.00, 4000.00),
  (4, 'E', 4000.00, 4500.00),
  (5, 'F', 4500.00, 5000.00),
  (6, 'G', 5000.00, 6000.00);

INSERT INTO companylaptop (id, companyLaptops, available, brandAndType, memory, diskspace, firstoperatingsystem, secondoperatingsystem) VALUES
  (0, 'Type1', true, 'Apple Macbook Pro 15', '32GB', '512GB', 'MacOSX', null),
  (1, 'Type2', true, 'HP ProBook G6', '32GB', '512GB', 'Windows', 'Linux'),
  (2, 'Type3', true, 'Dell XPS 13,3', '16GB', '512GB', 'Linux', null),
  (3, 'Type4', true, 'Dell XPS 15', '64GB', '1024GB', 'Windows', 'BSD'),
  (4, 'Type5', true, 'Apple Macbook Pro 13', '8GB', '128GB', 'MacOSX', null);

INSERT INTO ceworkingconditions (id, salarygroup, companycar, companylaptop) VALUES
  (0, 'A', false, 'Type5'),
  (1, 'B', false, 'Type5'),
  (2, 'C', true, 'Type3'),
  (3, 'D', true, 'Type3'),
  (4, 'E', true, 'Type2'),
  (5, 'F', true, 'Type1'),
  (6, 'G', true, 'Type4');