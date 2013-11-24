(define add
	(lambda (a b)
		(+ a b)))

(add 2 3)



(define proc
  (lambda (a b)
    (let ((sum (+ a b)))
      sum)
))

(define x 2)

(define func
  (lambda (a)
    (let* ((b 2)
           (prod (* a b)))
      prod)))



